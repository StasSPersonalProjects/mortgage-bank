import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import classes from "./styles/OtpConfirmationPage.module.css";
import InitialUserData from "../models/InitialUserData";
import { OTP_GENERATION_URL, OTP_VERIFICATION_URL } from "../utils/urls";
import { useDispatch } from "react-redux";
import { authActions } from "../store/auth";

export default function OtpConfirmationPage() {
  const [id, setId] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [otp, setOtp] = useState("");
  const [isDataEntered, setIsDataEntered] = useState(false);
  const [isAgreed, setIsAgreed] = useState(false);
  const [isDataSent, setIsDataSent] = useState(false);
  const location = useLocation();
  const currentPath = location.pathname;
  const dispatch = useDispatch();

  useEffect(() => {
    if (currentPath.endsWith("existing")) {
      setIsAgreed(true);
    }
  }, [currentPath]);

  const handleCheckboxChange = (event) => {
    setIsAgreed(event.target.checked);
  };

  let inputFields;

  if (currentPath.endsWith("new")) {
    inputFields = (
      <div className={classes["initial-input"]}>
        <input
          value={id}
          onChange={(e) => setId(e.target.value)}
          placeholder={"ID"}
          type="text"
          required
        />
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder={"E-mail"}
          type="text"
          required
        />
        <input
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          placeholder={"First name"}
          type="text"
          required
        />
        <input
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          placeholder={"Last name"}
          type="text"
          required
        />
        <input
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
          placeholder={"Phone number"}
          type="text"
          required
        />
      </div>
    );
  } else {
    inputFields = (
      <div className={classes["initial-input"]}>
        <input
          value={id}
          onChange={(e) => setId(e.target.value)}
          placeholder={"ID"}
          type="text"
          required
        />
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder={"E-mail"}
          type="text"
          required
        />
      </div>
    );
  }

  const handleUserDataSubmission = async (event) => {
    event.preventDefault();
    setIsDataEntered(true);
    try {
      const userData = new InitialUserData(
        id,
        email,
        firstName,
        lastName,
        phoneNumber
      );
      const response = await fetch(OTP_GENERATION_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      });

      if (!response.status === 204) {
        console.error("Something went wrong");
      }
      setIsDataSent(true);
    } catch (error) {
      console.error("Something went wrong", error);
    }
  };

  const handleOtpSubmission = async (event) => {
    event.preventDefault();
    try {
      const params = new URLSearchParams({
        id,
        otp,
      });
      const url = OTP_VERIFICATION_URL + `?${params.toString()}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        console.error("Something went wrong");
      }

      // receive a token and store it in session storage
      // update redux that user is now authenticated
      const resData = await response.json();
      console.log(resData);
      dispatch(authActions.login(resData));

      // redirect the user to the next page
    } catch (error) {
      console.error("Something went wrong", error);
    }
  };

  return (
    <form
      className={classes["main-form"]}
      onSubmit={isDataSent ? handleOtpSubmission : handleUserDataSubmission}
    >
      {isDataEntered ? (
        <div className={classes["initial-input"]}>
          <p>We sent you an email with a code</p>
          <input
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            placeholder={"Enter the code you received"}
            type="text"
            required
          />
        </div>
      ) : (
        inputFields
      )}

      {!isDataEntered && !currentPath.endsWith("existing") && (
        <label htmlFor="terms">
          <input
            type="checkbox"
            id="terms"
            name="terms"
            onChange={handleCheckboxChange}
            required
          />
          I agree that you will keep contacting me via e-mail
        </label>
      )}

      {isDataEntered ? (
        <button>Confirm</button>
      ) : (
        <button disabled={currentPath.endsWith("new") ? !isAgreed : false}>
          Send me verification code
        </button>
      )}
    </form>
  );
}
