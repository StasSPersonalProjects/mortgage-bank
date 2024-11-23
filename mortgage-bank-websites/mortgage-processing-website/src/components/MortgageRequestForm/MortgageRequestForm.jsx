/* eslint-disable react/prop-types */
import { useState } from "react";
import AccordionItem from "../AccordionItem/AccordionItem";
import classes from "./MortgageRequestForm.module.css";
import {
  FETCH_CONSULTANTS_URL,
  UPDATE_MORTGAGE_REQUEST_URL,
} from "../../utils/urls";
import { useDispatch, useSelector } from "react-redux";
import CustomersAccordionItem from "../CustomersAccordionItem/CustomersAccordionItem";
import { workFieldActions } from "../../store/workField";
import RealEstateMortgageAccordionItem from "../RealEstateMortgageAccordionItem/RealEstateMortgageAccordionItem";

export default function MortgageRequestForm({ data }) {
  const token = useSelector((state) => state.auth.token);
  const [availableConsultants, setAvailableConsultants] = useState({});
  const [selectedOwner, setSelectedOwner] = useState("");
  const dispatch = useDispatch();

  const handleConsultantsChange = async () => {
    const queryParams = new URLSearchParams({
      role: "CONSULTANT",
    });

    const fullURL = `${FETCH_CONSULTANTS_URL}?${queryParams.toString()}`;
    try {
      const response = await fetch(fullURL, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Error occurred while fetching available consultants.");
      }

      const responseData = await response.json();
      setAvailableConsultants(responseData);
    } catch (error) {
      console.error(error);
    }
  };

  const handleOwnerChange = (e) => {
    setSelectedOwner(e.target.value);
  };

  const handleOwnerChangeSubmit = async (event) => {
    event.preventDefault();

    const ownerUpdate = {
      id: data.id,
      fields: {
        owner: selectedOwner,
      },
    };

    try {
      const response = await fetch(UPDATE_MORTGAGE_REQUEST_URL, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(ownerUpdate),
      });

      if (!response.ok) {
        throw new Error("Error occurred while updating mortgage request.");
      }
      dispatch(workFieldActions.updateMortgageRequestOwner(selectedOwner));
      setSelectedOwner("");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={classes["mortgage-request-form"]}>
      <div className={classes["mortgage-request-header"]}>
        <button>{data.isPulled ? "Return to queue" : "Pull"}</button>
        <p>{data.pulledBy ? `Pulled by ${data.pulledBy}` : "In queue"}</p>
      </div>
      <AccordionItem title={data.owner ? `Owner: ${data.owner}` : "No owner"}>
        <form
          className={classes["owner-change-form"]}
          onSubmit={handleOwnerChangeSubmit}
        >
          <input
            list="available-consultants"
            placeholder="select new owner"
            onFocus={handleConsultantsChange}
            value={selectedOwner}
            onChange={handleOwnerChange}
          />
          <datalist id="available-consultants">
            {availableConsultants.length > 0 ? (
              availableConsultants.map((c) => (
                <option key={c.name} value={c.name} />
              ))
            ) : (
              <option value="" />
            )}
          </datalist>
          <button>Save</button>
        </form>
      </AccordionItem>
      <AccordionItem title={"Customers info"}>
        <CustomersAccordionItem
          title={"Borrowers"}
          data={data}
          dataField={"borrowers"}
        />
        <CustomersAccordionItem
          title={"Guarantees"}
          data={data}
          dataField={"guarantees"}
        />
      </AccordionItem>
      <AccordionItem title={"Real estate and mortgage"}>
        <RealEstateMortgageAccordionItem
          data={data}
          dataField={"realEstateProperties"}
        />
      </AccordionItem>
      <AccordionItem title={"Documents"}></AccordionItem>
      <AccordionItem title={"Decisions"}></AccordionItem>
    </div>
  );
}
