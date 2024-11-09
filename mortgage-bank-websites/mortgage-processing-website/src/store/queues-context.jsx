/* eslint-disable react-refresh/only-export-components */
/* eslint-disable react/prop-types */
import {
  createContext,
  useContext,
  useMemo,
  useState,
  useCallback,
} from "react";
import { FETCH_REQUESTS_URL } from "../utils/urls";

export const QueuesContext = createContext({
  initialRequestsQueue: [],
  awaitingDecisionRequestsQueue: [],
  returnedRequestsQueue: [],
  refreshQueues: () => {},
  queuesLoading: false,
  queuesErrorMessage: null,
});

export const useQueues = () => useContext(QueuesContext);

export default function QueuesContextProvider({ children }) {
  const [initialRequestsQueue, setInitialRequestsQueue] = useState([]);
  const [awaitingDecisionRequestsQueue, setAwaitingDecisionRequestsQueue] =
    useState([]);
  const [returnedRequestsQueue, setReturnedRequestsQueue] = useState([]);
  const [queuesLoading, setQueuesLoading] = useState(false);
  const [queuesErrorMessage, setQueuesErrorMessage] = useState(null);

  const fetchRequests = useCallback(async (queueType) => {
    setQueuesLoading(true);
    setQueuesErrorMessage(null);

    let attempts = 3;
    const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));
    let isMounted = true;

    for (let i = 0; i < attempts; i++) {
      try {
        const queryParam = new URLSearchParams({ queue_type: queueType });
        const fullURL = `${FETCH_REQUESTS_URL}?${queryParam.toString()}`;
        const response = await fetch(fullURL);
        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.message || "Failed to fetch pending requests");
        }
        const responseData = await response.json();

        if (isMounted) {
          switch (queueType) {
            case "INITIAL":
              setInitialRequestsQueue(responseData);
              break;
            case "AWAITING_DECISION":
              setAwaitingDecisionRequestsQueue(responseData);
              break;
            case "RETURNED":
              setReturnedRequestsQueue(responseData);
              break;
            default:
              return;
          }
          break;
        }
      } catch (error) {
        if (i < attempts - 1) {
          await delay(5000);
        } else {
          if (isMounted) {
            setQueuesErrorMessage(
              error.message || "An unexpected error occurred"
            );
          }
        }
      }
    }

    setQueuesLoading(false);

    return () => {
      isMounted = false;
    };
  }, []);

  const refreshQueues = useCallback(() => {
    fetchRequests("INITIAL");
    fetchRequests("AWAITING_DECISION");
    fetchRequests("RETURNED");
  }, [fetchRequests]);

  const queuesValues = useMemo(
    () => ({
      initialRequestsQueue,
      awaitingDecisionRequestsQueue,
      returnedRequestsQueue,
      refreshQueues,
      queuesLoading,
      queuesErrorMessage,
    }),
    [
      initialRequestsQueue,
      awaitingDecisionRequestsQueue,
      returnedRequestsQueue,
      refreshQueues,
      queuesLoading,
      queuesErrorMessage,
    ]
  );

  return (
    <QueuesContext.Provider value={queuesValues}>
      {children}
    </QueuesContext.Provider>
  );
}
