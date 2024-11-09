/* eslint-disable react/prop-types */
import { useSelector } from "react-redux";
import { useMemo } from "react";
import MortgageRequestsSearchResult from "../MortgageRequestsSearchResult/MortgageRequestsSearchResult";
import MortgageRequestForm from "../MortgageRequestForm/MortgageRequestForm";

export default function WorkTabContent({ displayContentFor }) {
  const workFieldTabs = useSelector((state) => state.workField.workFieldTabs);

  const activeTabIndex = useMemo(() => {
    return workFieldTabs.findIndex((el) => el.label === displayContentFor);
  }, [displayContentFor, workFieldTabs]);

  const type = useMemo(() => {
    if (workFieldTabs.length > 0) {
      return workFieldTabs[activeTabIndex].type;
    }
  }, [workFieldTabs, activeTabIndex]);

  const displayData = useMemo(() => {
    return workFieldTabs.find((el) => el.label === displayContentFor)?.data;
  }, [displayContentFor, workFieldTabs]);


  return (
    <>
      {type === "search-result-table" && (
        <MortgageRequestsSearchResult data={displayData} />
      )}
      {type === "mortgage-request" && (
        <MortgageRequestForm data={displayData} />
      )}
    </>
  );
}
