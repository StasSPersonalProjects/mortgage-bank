import AuthForm from "../components/AuthForm";
import classes from "./styles/LoginPage.module.css";

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
