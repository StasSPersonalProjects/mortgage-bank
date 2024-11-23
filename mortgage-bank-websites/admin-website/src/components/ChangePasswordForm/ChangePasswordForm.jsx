import { useState } from "react";
import { useSelector } from "react-redux";
import classes from "./ChangePasswordForm.module.css";
import ChangePasswordDto from "../../models/ChangePasswordDto";
import { CHANGE_PASSWORD_URL } from "../../utils/urls";

export default function ChangePasswordForm() {
  const token = useSelector((state) => state.auth.token);
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [passwordChangeResult, setPasswordChangeResult] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handlePasswordChange = async (event) => {
    event.preventDefault();
    const passwordChangeData = new ChangePasswordDto(
      currentPassword,
      newPassword,
      confirmPassword
    );

    setCurrentPassword("");
    setNewPassword("");
    setConfirmPassword("");

    try {
      const response = await fetch(CHANGE_PASSWORD_URL, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(passwordChangeData),
      });

      if (!response.ok) {
        setPasswordChangeResult("");
        const errorData = await response.json();
        throw new Error(errorData.error || "Failed to change the password.");
      }

      setErrorMessage("");
      const responseData = await response.json();
      setPasswordChangeResult(responseData.message);
    } catch (error) {
      setErrorMessage(
        error.message || "An error occurred while searching for an employee."
      );
    }
  };

  return (
    <div className={classes["form-container"]}>
      <form onSubmit={handlePasswordChange}>
        <div className={classes.inputs}>
          <input
            className={classes.input}
            type="password"
            placeholder="Current Password"
            value={currentPassword}
            onChange={(e) => setCurrentPassword(e.target.value)}
            required
          />
          <input
            className={classes.input}
            type="password"
            placeholder="New Password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <input
            className={classes.input}
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Submit</button>
      </form>
      {passwordChangeResult && (
        <div className={classes["success-message"]}>
          <p>{passwordChangeResult}</p>
        </div>
      )}
      {errorMessage && (
        <div className={classes["error-message"]}>
          <p>{errorMessage}</p>
        </div>
      )}
    </div>
  );
}
