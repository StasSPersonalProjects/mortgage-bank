import classes from "./styles/RequestNavigator.module.css";

export default function RequestNavigator() {
  return (
    <nav className={classes["nav-bar"]}>
      <ul>
        <li>
          <span>Request data</span>
        </li>
        <li>
          <span>Mortgage composition</span>
        </li>
      </ul>
    </nav>
  );
}
