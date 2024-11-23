/* eslint-disable react/prop-types */
import { useState } from "react";
import { useSelector } from "react-redux";
import {
  CHANGE_ROLES_URL,
  DEACTIVATION_URL,
  FIND_USER_URL,
  RESTORATION_URL,
} from "../../utils/urls";
import classes from "./ChangeEmployeeData.module.css";
import ChangeRolesDto from "../../models/ChangeRolesDto";

const searchResultInitialState = {
  id: null,
  fullName: "",
  username: "",
  roles: [],
  isActive: false,
};

export default function ChangeEmployeeData({
  availableRoles,
  noRolesFetched,
  rolesFetchError,
}) {
  const [searchBy, setSearchBy] = useState("");
  const [searchParam, setSearchParam] = useState("");
  const [searchResult, setSearchResult] = useState(searchResultInitialState);
  const [searchErrorMessage, setSearchErrorMessage] = useState("");
  const [updateEmployeeErrorMessage, setUpdateEmployeeErrorMessage] =
    useState("");

  const [isChangingRole, setIsChangingRole] = useState(false);
  const [assignedRoles, setAssignedRoles] = useState([]);

  const token = useSelector((state) => state.auth.token);

  const handleRadioChange = (event) => {
    setSearchBy(event.target.value);
  };

  const handleRoleChange = (event, role) => {
    setIsChangingRole(true);
    if (event.target.checked) {
      setAssignedRoles((prevRoles) => [...prevRoles, role]);
    } else {
      setAssignedRoles((prevRoles) => prevRoles.filter((r) => r !== role));
    }
  };

  const handleRoleChangeSubmit = async (event) => {
    event.preventDefault();

    const queryParams = new URLSearchParams({
      id: searchResult.id,
    });

    const fullUrl = `${CHANGE_ROLES_URL}?${queryParams.toString()}`;

    const changedRoles = new ChangeRolesDto(assignedRoles);
    setAssignedRoles([]);

    try {
      const response = await fetch(fullUrl, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(changedRoles),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error || "Failed to update roles.");
      }
      setIsChangingRole(false);
      setSearchResult(searchResultInitialState);
    } catch (error) {
      setUpdateEmployeeErrorMessage(
        error.message ||
          "An error occurred while trying to update employee data."
      );
    }
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();

    const queryParams = new URLSearchParams({
      search_by: searchBy,
      search_param: searchParam,
    });

    const fullUrl = `${FIND_USER_URL}?${queryParams.toString()}`;

    try {
      const response = await fetch(fullUrl, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        const errorData = await response.json();
        setSearchResult(searchResultInitialState);
        throw new Error(errorData.error || "Failed to find the employee.");
      }

      const responseData = await response.json();
      setSearchErrorMessage("");
      setUpdateEmployeeErrorMessage("");
      setSearchResult(responseData);
      setSearchParam("");
    } catch (error) {
      setSearchErrorMessage(
        error.message || "An error occurred while searching for an employee."
      );
    }
  };

  const handleEmployeeDataUpdate = async (action) => {
    const queryParams = new URLSearchParams({
      id: searchResult.id,
    });

    const fullUrl =
      action === "deactivate"
        ? `${DEACTIVATION_URL}?${queryParams.toString()}`
        : `${RESTORATION_URL}?${queryParams.toString()}`;

    try {
      const response = await fetch(fullUrl, {
        method: "PATCH",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.error || "Failed to update the employee data."
        );
      }
      setUpdateEmployeeErrorMessage("");
      setSearchErrorMessage("");

      setSearchResult(searchResultInitialState);
    } catch (error) {
      setUpdateEmployeeErrorMessage(
        error.message ||
          "An error occurred while trying to update employee data."
      );
    }
  };

  return (
    <div className={classes["form-container"]}>
      <form onSubmit={handleSearchSubmit}>
        <p className={classes["form-title"]}>Search by:</p>

        <div className={classes["radio-group"]}>
          <input
            type="radio"
            name="searchBy"
            id="search-by-id"
            value="idCardNumber"
            onChange={handleRadioChange}
          />
          <label htmlFor="search-by-id">ID Card Number</label>
        </div>

        <div className={classes["radio-group"]}>
          <input
            type="radio"
            name="searchBy"
            id="search-by-fullname"
            value="fullName"
            onChange={handleRadioChange}
          />
          <label htmlFor="search-by-fullname">Full Name</label>
        </div>

        <div className={classes["radio-group"]}>
          <input
            type="radio"
            name="searchBy"
            id="search-by-username"
            value="username"
            onChange={handleRadioChange}
          />
          <label htmlFor="search-by-username">Username</label>
        </div>

        <div className={classes["input-group"]}>
          <input
            type="text"
            required
            value={searchParam}
            onChange={(e) => setSearchParam(e.target.value)}
            className={classes["text-input"]}
          />
        </div>

        <button type="submit" className={classes["submit-btn"]}>
          Find Employee
        </button>
      </form>
      {searchErrorMessage && (
        <div className={classes["error-message"]}>
          <p>{searchErrorMessage}</p>
        </div>
      )}

      {searchResult && searchResult.id && (
        <div className={classes["result-container"]}>
          <p>
            {`Status: `}
            <span
              className={
                searchResult.isActive
                  ? classes["active-status"]
                  : classes["inactive-status"]
              }
            >
              {searchResult.isActive ? `active` : `inactive`}
            </span>
          </p>
          <p>{`ID: ${searchResult.id}`}</p>
          <p>{`Full Name: ${searchResult.fullName}`}</p>
          <p>{`Username: ${searchResult.username}`}</p>
          <p>
            {`Last Login: ${
              searchResult.lastLogin === null ||
              searchResult.lastLogin === undefined
                ? "---"
                : new Date(searchResult.lastLogin).toLocaleString()
            }`}
          </p>

          <div className={classes.roles}>
            <p className={classes["roles-label"]}>
              Roles:{" "}
              {Array.isArray(searchResult.roles)
                ? searchResult.roles.map((r) => (
                    <span key={r.id} className={classes["roles-container"]}>
                      {r.role}
                    </span>
                  ))
                : "No roles available"}
            </p>
            {isChangingRole &&
              (noRolesFetched ? (
                <p>
                  {rolesFetchError ? rolesFetchError : "Failed to load roles."}
                </p>
              ) : (
                <div>
                  <form
                    className={classes["roles-selection"]}
                    onSubmit={handleRoleChangeSubmit}
                  >
                    {availableRoles.map((r) => (
                      <div key={r.id}>
                        <input
                          type="checkbox"
                          id={`role-${r.id}`}
                          name="role"
                          checked={assignedRoles.includes(r.role)}
                          onChange={(e) => handleRoleChange(e, r.role)}
                        />
                        <label htmlFor={`role-${r.id}`}>
                          {r.role.toLowerCase()}
                        </label>
                      </div>
                    ))}
                    <button
                      className={classes["change-button"]}
                      disabled={assignedRoles.length === 0}
                    >
                      Submit
                    </button>
                  </form>
                </div>
              ))}
            {isChangingRole ? (
              <button
                className={classes["change-button"]}
                onClick={() => setIsChangingRole(false)}
              >
                Close
              </button>
            ) : (
              <button
                className={classes["change-button"]}
                onClick={handleRoleChange}
              >
                Change
              </button>
            )}
          </div>

          <div className={classes["button-group"]}>
            <button
              onClick={() => handleEmployeeDataUpdate("deactivate")}
              disabled={!searchResult.isActive}
            >
              Deactivate
            </button>
            <button
              onClick={() => handleEmployeeDataUpdate("restore")}
              disabled={searchResult.isActive}
            >
              Restore
            </button>
          </div>
          {updateEmployeeErrorMessage && (
            <div className={classes["error-message"]}>
              <p>{updateEmployeeErrorMessage}</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
