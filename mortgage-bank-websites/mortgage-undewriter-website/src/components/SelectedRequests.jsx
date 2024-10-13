import SelectedRequest from "./SelectedRequest";

/* eslint-disable react/prop-types */
export default function SelectedRequests({ selectedRequests }) {
  return (
    <nav>
      <ul>
        {selectedRequests.map((r) => (
          <SelectedRequest key={r.id} request={r} />
        ))}
      </ul>
    </nav>
  );
}
