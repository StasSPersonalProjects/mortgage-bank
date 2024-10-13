import { useSelector } from "react-redux";
import AccordionItem from "../components/AccordionItem";
import ChangeEmployeeData from "../components/ChangeEmplyeeData";
import RegisterNewUserForm from "../components/RegisterNewUserForm";
import classes from "./styles/ManageUsersPage.module.css";
import { useState, useEffect } from "react";
import { GET_AVAILABLE_ROLES_URL } from "../utils/urls";

export default function ManageUsersPage() {
  const [availableRoles, setAvailableRoles] = useState([]);
  const [noRolesFetched, setNoRolesFetched] = useState(false);
  const [rolesFetchError, setRolesFetchError] = useState("");
  const token = useSelector((state) => state.auth.token);

  useEffect(() => {
    if (!token) return;

    async function fetchRoles() {
      try {
        const response = await fetch(GET_AVAILABLE_ROLES_URL, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch roles. Please try again later.");
        }
        const responseData = await response.json();
        setAvailableRoles(responseData);
      } catch (error) {
        setNoRolesFetched(true);
        setRolesFetchError(error.message || "Unknown error occurred");
      }
    }

    fetchRoles();
  }, [token]);

  return (
    <main className={classes.main}>
      <section>
        <AccordionItem title="Register New Employee">
          <RegisterNewUserForm
            availableRoles={availableRoles}
            noRolesFetched={noRolesFetched}
            rolesFetchError={rolesFetchError}
          />
        </AccordionItem>
      </section>
      <section>
        <AccordionItem title="Deactivate / Restore / Change Permission">
          <ChangeEmployeeData
            availableRoles={availableRoles}
            noRolesFetched={noRolesFetched}
            rolesFetchError={rolesFetchError}
          />
        </AccordionItem>
      </section>
    </main>
  );
}
