/* eslint-disable react/prop-types */
import classes from "./QueueTab.module.css";

export default function QueueTab({ label, active, onClick }) {
  return (
    <div
      className={`${classes["queue-tab"]} ${active ? classes.active : ''}`}
      onClick={onClick}
    >
      {label}
    </div>
  );
};

