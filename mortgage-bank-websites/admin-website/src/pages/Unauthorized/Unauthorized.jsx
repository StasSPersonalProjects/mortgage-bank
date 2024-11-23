import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Unauthorized.module.css";

export default function Unauthorized() {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/");
    }, 4000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className={classes.message}>
      <h1>You are unauthorized to access this page!</h1>
      <h2>Redirecting you back...</h2>
    </div>
  );
}
