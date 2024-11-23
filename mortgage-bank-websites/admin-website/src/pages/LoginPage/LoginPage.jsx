import AuthForm from "../../components/AuthForm/AuthForm";
import classes from "./LoginPage.module.css";

export default function LoginPage() {
  return (
    <div className={classes.container}>
      <p className={classes["container-text"]}>
        Administartion And Configurations Website
      </p>
      <AuthForm />
    </div>
  );
}
