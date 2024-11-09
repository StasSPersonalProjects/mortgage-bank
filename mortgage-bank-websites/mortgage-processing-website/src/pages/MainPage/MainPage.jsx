import { useEffect, useState } from "react";
import classes from "./MainPage.module.css";
import QueueTab from "../../components/QueueTab/QueueTab";
import QueueTable from "../../components/QueueTable/QueueTable";
import { FIND_REQUESTS_URL } from "../../utils/urls";
import { useQueues } from "../../store/queues-context";
import { useDispatch, useSelector } from "react-redux";
import { workFieldActions } from "../../store/workField";
import WorkTabsContainer from "../../components/WorkTabsContainer/WorkTabsContainer";
import WorkTabContent from "../../components/WorkTabContent/WorkTabContent";

export default function MainPage() {
  const searchByOptions = ["ID card number", "Request number"];
  const searchByMap = {
    "ID card number": "idCardNumber",
    "Request number": "requestNumber",
  };
  const [searchBy, setSearchBy] = useState("ID card number");
  const [searchValue, setSearchValue] = useState("");
  const [activeTab, setActiveTab] = useState("initialRequests");
  const {
    initialRequestsQueue,
    awaitingDecisionRequestsQueue,
    returnedRequestsQueue,
    refreshQueues,
    queuesLoading,
    queuesErrorMessage,
  } = useQueues();
  const isWorkFieldActive = useSelector(
    (state) => state.workField.isWorkFieldActive
  );
  const activeWorkTab = useSelector((state) => state.workField.active);
  const dispatch = useDispatch();

  useEffect(() => {
    refreshQueues();
  }, [refreshQueues]);

  const handleSearchOptionChange = (e) => {
    setSearchBy(e.target.value);
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    const queryParams = new URLSearchParams({
      search_param: searchByMap[searchBy],
      search_value: searchValue,
    });

    const fullURL = `${FIND_REQUESTS_URL}?${queryParams.toString()}`;

    try {
      const response = await fetch(fullURL);
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData);
      }
      const responseData = await response.json();
      const searchResult = {
        id: responseData.id ? responseData.id : searchValue,
        type:
          searchBy === "Request number"
            ? "mortgage-request"
            : "search-result-table",
        data: responseData,
        label:
          searchBy === "Request number"
            ? `Request #${responseData.id}`
            : `Requests for ${searchValue}`,
      };
      dispatch(workFieldActions.enableWorkField());
      dispatch(workFieldActions.addWorkFieldTab(searchResult));
      dispatch(workFieldActions.setActive(searchResult.label));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={classes["main-page"]}>
      <div className={classes["work-menu"]}>
        <div className={classes["menu"]}>
          <div className={classes["search"]}>
            <form
              className={classes["search-form"]}
              onSubmit={handleSearchSubmit}
            >
              <div className={classes["search-options"]}>
                <label htmlFor="search-options">Search by:</label>
                <select
                  name="search-options"
                  id="search-options"
                  onChange={handleSearchOptionChange}
                  value={searchBy}
                >
                  {searchByOptions.map((option) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>
              </div>
              <div className={classes["search-input"]}>
                <input
                  placeholder="search value"
                  type="text"
                  value={searchValue}
                  onChange={(e) => setSearchValue(e.target.value)}
                  required
                />
                <button className={classes["search-btn"]}>Find</button>
              </div>
            </form>
          </div>
          <button className={classes["new-request-btn"]}>
            Create New Request
          </button>
        </div>
        <div className={classes["pending-requests"]}>
          <div className={classes["tabs-container"]}>
            <QueueTab
              label="Initial"
              active={activeTab === "initialRequests"}
              onClick={() => setActiveTab("initialRequests")}
            />
            <QueueTab
              label="Awaiting"
              active={activeTab === "awaitingDecisionRequests"}
              onClick={() => setActiveTab("awaitingDecisionRequests")}
            />
            <QueueTab
              label="Returned"
              active={activeTab === "returnedRequests"}
              onClick={() => setActiveTab("returnedRequests")}
            />
          </div>
          {queuesLoading && <img src="/tube-spinner-queues.svg" />}
          {!queuesErrorMessage ? (
            <div className={classes["tab-content"]}>
              {activeTab === "initialRequests" && (
                <QueueTable requests={initialRequestsQueue} />
              )}
              {activeTab === "awaitingDecisionRequests" && (
                <QueueTable requests={awaitingDecisionRequestsQueue} />
              )}
              {activeTab === "returnedRequests" && (
                <QueueTable requests={returnedRequestsQueue} />
              )}
            </div>
          ) : (
            <p>{queuesErrorMessage}</p>
          )}
        </div>
      </div>
      <div className={isWorkFieldActive ? classes["work-field"] : ""}>
        <WorkTabsContainer />
        <WorkTabContent displayContentFor={activeWorkTab} />
      </div>
    </div>
  );
}
