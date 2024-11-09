/* eslint-disable react/prop-types */
import { useDispatch } from "react-redux";
import classes from "./WorkTab.module.css";
import { workFieldActions } from "../../store/workField";

export default function WorkTab({ id, label, onClick, active }) {

  const dispatch = useDispatch();

  const handleRemoveClick = (event) => {
    event.stopPropagation();
    dispatch(workFieldActions.removeWorkFieldTab(id));
  };

  return (
    <div
      className={`${classes["work-tab"]} ${active ? classes.active : ""}`}
      onClick={onClick}
    >
      <p>{label}</p>
      <button onClick={handleRemoveClick}>X</button>
    </div>
  );
}
