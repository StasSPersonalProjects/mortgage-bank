import RequestsList from "../components/RequestsList";
import SelectedRequests from "../components/SelectedRequest";
import classes from "./styles/MainWorkPage.module.css";

export default function MainWorkPage() {
  return (
    <div className={classes.container}>
      <div className={classes["requests-list"]}>
        <RequestsList />
      </div>
      <div>
        <SelectedRequests SelectedRequests={null}/>
      </div>
    </div>
  );
}
