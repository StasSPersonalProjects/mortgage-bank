/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import AccordionItem from "../AccordionItem/AccordionItem";
import MortgageTable from "../MortgageTable/MortgageTable";

const propertyStatusValues = {
  SINGLE: "Single",
  ALTERNATIVE: "Alternative",
  ADDITIONAL: "Additional",
};

const propertyTypeValues = {
  PRIVATE_HOUSE: "Private house",
  APARTMENT: "Appartment",
  LAND: "Land",
};

export default function RealEstateMortgageAccordionItem({ data, dataField }) {
  return (
    <>
      {data[dataField].map((r, index) => (
        <AccordionItem
          key={index}
          title={
            r.city
              ? `${r.city} ${r.securityNumber ? r.securityNumber : ""}`
              : "---"
          }
        >
          <AccordionItem title={"Property"}></AccordionItem>
          <AccordionItem title={"Mortgage composition"}>
            <MortgageTable
              loans={data[dataField][index].mortgageComposition.loans}
              totalAmount={
                data[dataField][index].mortgageComposition.totalAmount
              }
            />
          </AccordionItem>
        </AccordionItem>
      ))}
    </>
  );
}
