import { useState, useRef } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import CustomModal from "../CustomModal/CustomModal";
import AuthDto from "../../models/AuthDto";
import { authActions } from "../../store/auth";
import classes from "./AuthForm.module.css";
import { AUTHENTICATION_URL } from "../../utils/urls";

export default function AuthForm() {
  const modal = useRef();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleAuthFormSubmittion = async (event) => {
    event.preventDefault();

    const authData = new AuthDto(username, password);
    setUsername("");
    setPassword("");

    let attempts = 3;
    const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

    for (let i = 0; i < attempts; i++) {
      try {
        setIsLoading(true);
        const response = await fetch(AUTHENTICATION_URL, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(authData),
        });

        if (!response.ok) {
          const errorData = await response.json();
          setIsLoading(false);
          setErrorMessage(errorData.error);
          modal.current.open();
          return;
        }

        const responseData = await response.json();
        dispatch(authActions.authenticate(responseData));
        setIsLoading(false);
        navigate("/main", { replace: true });
        break;
      } catch (error) {
        if (i < attempts - 1) {
          await delay(5000);
        } else {
          setIsLoading(false);

          if (
            error instanceof TypeError &&
            error.message === "Failed to fetch"
          ) {
            setErrorMessage("Server unavailable");
          } else {
            setErrorMessage("An error occurred: " + error.message);
          }
          modal.current.open();
        }
      }
    }
  };

  return (
    <>
      <CustomModal ref={modal} message={errorMessage} />
      <form className={classes["auth-form"]} onSubmit={handleAuthFormSubmittion}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button className={classes.btn}>
          {isLoading ? (
            <p className={classes["btn-text"]}>
              <img className={classes["btn-img"]} src="/tube-spinner.svg" />
              {"Authenticating..."}
            </p>
          ) : (
            <p className={classes["btn-text"]}>Sign In</p>
          )}
        </button>
      </form>
    </>
  );
}
