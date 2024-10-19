import classes from "./styles/ErrorPage.module.css";

export default function ErrorPage() {
  return (
    <div className={classes.message}>
      <h1>Unexpected error occurred!</h1>
    </div>
  );
}
