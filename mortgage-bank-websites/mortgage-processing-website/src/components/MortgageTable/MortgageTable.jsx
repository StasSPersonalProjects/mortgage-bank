/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { useState } from "react";
import classes from "./MortgageTable.module.css";
import { useDispatch } from "react-redux";

const loanTypes = {
  FIXED_RATE_NON_INDEXED: "Fixed non-indexed",
  FIXED_RATE_INDEX_LINKED: "Fixed index-linked",
  PRIME_RATE: "Prime",
  ADJUSTABLE_RATE_X2_NON_INDEXED: "Adjustable 2+2 non-indexed",
  ADJUSTABLE_RATE_X2_INDEX_LINKED: "Adjustable 2+2 index-linked",
  ADJUSTABLE_RATE_X5_NON_INDEXED: "Adjustable 5+5 non-indexed",
  ADJUSTABLE_RATE_X5_INDEX_LINKED: "Adjustable 5+5 index-linked",
};

export default function MortgageTable({ loans, totalAmount }) {
  const [selectedLoanType, setSelectedLoanType] = useState("");
  const [calculatedPayment, setCalculatedPayment] = useState(0.0);
  const [calculatedMargin, setCalculatedMargin] = useState(0.0);

  const dispatch = useDispatch();

  const calculateDurationSteps = () => {
    switch (selectedLoanType) {
      case "Adjustable 2+2 non-indexed":
        return 24;
      case "Adjustable 2+2 index-linked":
        return 24;
      case "Adjustable 5+5 non-indexed":
        return 60;
      case "Adjustable 5+5 index-linked":
        return 60;
      default:
        return 12;
    }
  };

  const calculateMinDuration = () => {
    switch (selectedLoanType) {
      case "Adjustable 2+2 non-indexed":
        return 48;
      case "Adjustable 2+2 index-linked":
        return 48;
      case "Adjustable 5+5 non-indexed":
        return 120;
      case "Adjustable 5+5 index-linked":
        return 120;
      default:
        return 12;
    }
  }

  const handleSelectLoanType = (value, path) => {
    setSelectedLoanType(loanTypes[value]);
  };

  return (
    <div className={classes["container"]}>
      <table className={classes["loans-table"]}>
        <thead>
          <tr>
            <th>Loan type</th>
            <th>Amount</th>
            <th>Duration</th>
            <th>Basic rate</th>
            <th>Add-margin</th>
            <th>Interest</th>
            <th>Payment</th>
            <th>{"Bank's margin"}</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {loans.map((loan, index) => (
            <tr key={index}>
              <td>
                <select
                  defaultValue={loan.loanType}
                  onBlur={(e) => handleSelectLoanType(e.target.value)}
                >
                  {Object.entries(loanTypes).map(([key, value]) => (
                    <option key={key} value={key}>
                      {value}
                    </option>
                  ))}
                </select>
              </td>
              <td>
                <input type="number" step="10000" min="5000" />
              </td>
              <td>
                <input
                  type="number"
                  step={calculateDurationSteps()}
                  min={calculateMinDuration()}
                  max="360"
                />
              </td>
              <td></td>
              <td></td>
              <td></td>
              <td>{calculatedPayment.toFixed(2)}</td>
              <td>{calculatedMargin.toFixed(2)}</td>
              <td>
                <button>Calculate</button>
                <button>Remove</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className={classes["total-container"]}>
        <button>Add loan</button>
        <p>{`Total amount: ${new Intl.NumberFormat("en-US").format(
          totalAmount
        )} ILS`}</p>
      </div>
    </div>
  );
}
