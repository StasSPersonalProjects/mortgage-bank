import { useDispatch, useSelector } from "react-redux";
import WorkTab from "../WorkTab/WorkTab";
import { workFieldActions } from "../../store/workField";
import classes from "./WorkTabsContainer.module.css";

export default function WorkTabsContainer() {
  const workFieldTabs = useSelector((state) => state.workField.workFieldTabs);
  const activeTab = useSelector((state) => state.workField.active);
  const dispatch = useDispatch();

  return (
    <div className={classes["work-tabs-container"]}>
      {workFieldTabs.map((workTab) => (
        <WorkTab
          key={workTab.id}
          id={workTab.id}
          label={workTab.label}
          active={activeTab === workTab.label}
          onClick={() => dispatch(workFieldActions.setActive(workTab.label))}
        />
      ))}
    </div>
  );
}
