import { NavLink } from "react-router-dom";
import classes from "./styles/NavigationBar.module.css";

export default function NavigationBar() {
  return (
    <nav className={classes["nav-bar"]}>
      <ul>
        <li>
          <NavLink to="/">Home</NavLink>
        </li>
        <li>
          <NavLink to="/contacts">Contact</NavLink>
        </li>
      </ul>
    </nav>
  );
}
