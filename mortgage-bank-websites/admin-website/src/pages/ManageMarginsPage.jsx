import { useEffect, useState } from "react";
import AccordionItem from "../components/AccordionItem";
import CreateLoanTypeForm from "../components/CreateLoanTypeForm";
import classes from "./styles/ManageMarginsPage.module.css";
import { useSelector } from "react-redux";
import { GET_AVAILABLE_LOAN_TYPES_URL } from "../utils/urls";

export default function ManageMarginsPage() {
  const token = useSelector((state) => state.auth.token);

  const [availableLoanTypes, setAvailableLoanTypes] = useState([]);

  useEffect(() => {
    const fetchAvailableLoanTypes = async () => {
      try {
        const response = await fetch(GET_AVAILABLE_LOAN_TYPES_URL, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          }
        });

        if (!response.ok) {
          throw new Error("");
        }

        const responseData = await response.json();
        setAvailableLoanTypes(responseData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchAvailableLoanTypes();
  }, [token]);

  return (
    <main className={classes.main}>
      <section>
        <AccordionItem title="Create Loan Type">
          <CreateLoanTypeForm availableLoanTypes={availableLoanTypes} />
        </AccordionItem>
      </section>
      <section>
        <AccordionItem title="Update Loan Type"></AccordionItem>
      </section>
      <section>
        <AccordionItem title="Delete Loan Type"></AccordionItem>
      </section>
    </main>
  );
}
