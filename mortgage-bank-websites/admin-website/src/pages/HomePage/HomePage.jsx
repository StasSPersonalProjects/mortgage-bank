import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import HomePageComponent from "../../components/HomePageComponent/HomePageComponent";
import classes from "./HomePage.module.css";
import { GET_ACTIVE_EMPLOYEES_URL } from "../../utils/urls";

export default function HomePage() {
  const token = useSelector((state) => state.auth.token);
  const [activeEmployees, setActiveEmployees] = useState(0);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (!token) return;

    const fetchActiveEmployees = async () => {
      try {
        const response = await fetch(GET_ACTIVE_EMPLOYEES_URL, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.error);
        }

        setErrorMessage("");
        const responseData = await response.json();

        setActiveEmployees(responseData.activeEmployees);
      } catch (error) {
        setErrorMessage(
          error.message ||
            "An error occurred while fetching number of active employees."
        );
      }
    };

    fetchActiveEmployees();
  }, [token]);

  return (
    <main className={classes.main}>
      <section>
        <HomePageComponent
          title={"Current number of active employees"}
          data={activeEmployees}
          error={errorMessage}
        />
      </section>
      <section>
        <HomePageComponent
          title={"last margins update"}
          data={"will be fetched"}
        />
      </section>
      <section>
        <HomePageComponent
          title={"last basic rates update"}
          data={"will be fetched"}
        />
      </section>
    </main>
  );
}
