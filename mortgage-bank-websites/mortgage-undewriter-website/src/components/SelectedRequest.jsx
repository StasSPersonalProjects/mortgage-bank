/* eslint-disable react/prop-types */
import RequestNavigator from "../components/RequestNavigator";
import WorkSheet from "../components/WorkSheet";

export default function SelectedRequest({ request }) {
  return (
    <div>
      <p>Request #{request.id}</p>
      <div>
        <RequestNavigator />
        <WorkSheet />
      </div>
    </div>
  );
}
