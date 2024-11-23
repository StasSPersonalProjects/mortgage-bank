import { useDispatch, useSelector } from "react-redux";
import { NavLink } from "react-router-dom";
import { logoutUser } from "../../store/auth";
import classes from "./NavigationBar.module.css";

export default function NavigationBar() {
  const token = useSelector((state) => state.auth.token);
  const dispatch = useDispatch();

  return (
    <nav className={classes["nav-bar"]}>
      <ul>
        <li>
          <NavLink to="/home">Home</NavLink>
        </li>
        <li>
          <NavLink to="/users">Users</NavLink>
        </li>
        <li>
          <NavLink to="/margins">Margins</NavLink>
        </li>
        <li>
          <NavLink to="/interest_rates">Interest Rates</NavLink>
        </li>
        <li>
          <NavLink to="/account">Account</NavLink>
        </li>
        <li>
          <NavLink
            to="/"
            onClick={() => {
              dispatch(logoutUser(token));
            }}
          >
            Sign Out
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
