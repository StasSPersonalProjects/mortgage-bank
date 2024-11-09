/* eslint-disable react/prop-types */
import classes from "./QueueTable.module.css";

export default function QueueTable({ requests, onClick }) {
  return (
    <table className={classes["requests-table"]}>
      <tbody className={classes["requests-table-body"]}>
        {requests.map((request) => (
          <tr
            key={request.id}
            className={classes["requests-table-row"]}
            onClick={() => onClick(request.id)}
          >
            <td>
              {new Intl.DateTimeFormat("en-US", {
                year: "numeric",
                month: "2-digit",
                day: "2-digit",
              }).format(new Date(request.transferTime))}
            </td>
            <td>{request.id}</td>
            <td>{request.borrowersLastName}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
