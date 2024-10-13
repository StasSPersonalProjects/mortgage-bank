/* eslint-disable react/prop-types */
import { useState } from "react";
import { NEW_EMPLOYEE_REGISTRATION_URL } from "../utils/urls";
import { useSelector } from "react-redux";
import classes from "./styles/RegisterNewUserForm.module.css";
import RegisterationDto from "../models/RegisterationDto";

export default function RegisterNewUserForm({
  availableRoles,
  noRolesFetched,
  rolesFetchError,
}) {
  const [idCardNumber, setIdCardNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [assignedRoles, setAssignedRoles] = useState([]);

  const [registrationError, setRegistrationError] = useState("");
  const [newEmployeeUsername, setNewEmployeeUsername] = useState("");

  const token = useSelector((state) => state.auth.token);

  const handleRoleChange = (event, role) => {
    if (event.target.checked) {
      setAssignedRoles((prevRoles) => [...prevRoles, role.role]);
    } else {
      setAssignedRoles((prevRoles) => prevRoles.filter((r) => r !== role.role));
    }
  };

  const handleRegistrationFormSubmit = async (event) => {
    event.preventDefault();

    const registrationData = new RegisterationDto(
      idCardNumber,
      firstName,
      lastName,
      assignedRoles
    );

    setIdCardNumber("");
    setFirstName("");
    setLastName("");
    setAssignedRoles([]);

    try {
      const response = await fetch(NEW_EMPLOYEE_REGISTRATION_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(registrationData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.error || "Failed to register a new employee."
        );
      }

      const responseData = await response.json();
      setNewEmployeeUsername(responseData.message);
      setRegistrationError("");
    } catch (error) {
      setRegistrationError(
        error.message || "An error occurred while registering the employee."
      );
    }
  };

  const handleReset = (type) => {
    if (type === "username") {
      setNewEmployeeUsername("");
    } else if (type === "register-error") {
      setRegistrationError("");
    }
  };

  return (
    <div className={classes["form-container"]}>
      <form onSubmit={handleRegistrationFormSubmit}>
        <div className={classes["initial-inputs"]}>
          <input
            className={classes["initial-input"]}
            type="text"
            placeholder="ID Card Number"
            value={idCardNumber}
            onChange={(e) => setIdCardNumber(e.target.value)}
            required
          />
          <input
            className={classes["initial-input"]}
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
          <input
            className={classes["initial-input"]}
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
        {noRolesFetched ? (
          <p className={classes["error-message"]}>
            {rolesFetchError ? rolesFetchError : "Failed to load roles."}
          </p>
        ) : (
          <div className={classes["checkbox-group"]}>
            <p>Assign roles:</p>
            {availableRoles.map((r) => (
              <div key={r.id}>
                <input
                  type="checkbox"
                  id={`role-${r.id}`}
                  name="role"
                  checked={assignedRoles.includes(r.role)}
                  onChange={(e) => handleRoleChange(e, r)}
                />
                <label htmlFor={`role-${r.id}`}>{r.role.toLowerCase()}</label>
              </div>
            ))}
          </div>
        )}
        <button type="submit">Submit</button>
      </form>
      {newEmployeeUsername ? (
        <div className={classes["success-message"]}>
          <p>{newEmployeeUsername}</p>
          <button onClick={() => handleReset("username")}>OK</button>
        </div>
      ) : (
        registrationError && (
          <div className={classes["error-message"]}>
            <p>{registrationError}</p>
            <button onClick={() => handleReset("register-error")}>OK</button>
          </div>
        )
      )}
    </div>
  );
}
