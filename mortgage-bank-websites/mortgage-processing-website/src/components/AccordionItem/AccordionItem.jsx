/* eslint-disable react/prop-types */
import { useState } from "react";
import classes from "./AccordionItem.module.css";

export default function AccordionItem({ children, title }) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className={classes["accordion-item"]}>
      <div onClick={toggleOpen} className={classes["accordion-header"]}>
        <p className={classes["accordion-title"]}>{title}</p>
        <span className={classes["accordion-icon"]}>{isOpen ? "-" : "+"}</span>
      </div>
      {isOpen && <div className={classes["accordion-content"]}>{children}</div>}
    </div>
  );
}