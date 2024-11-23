/* eslint-disable react/prop-types */
import classes from "./HomePageComponent.module.css";

export default function HomePageComponent({ data, title, error }) {
  return (
    <div className={classes.container}>
      {!error ? <p>{`${title} : ${data}`}</p> : <p>{error}</p>}
    </div>
  );
}
