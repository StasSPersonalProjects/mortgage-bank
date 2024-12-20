import PropTypes from "prop-types";
import { useState } from "react";
import { useSelector } from "react-redux";
import LoanRateTypeDto from "../../models/LoanRateTypeDto";
import { SET_ZERO_MARGINS_URL } from "../../utils/urls";
import classes from "./SetZeroMarginRatesForm.module.css";

const loanTypesMap = {
  "Fixed interest rate": "FIXED_RATE_NON_INDEXED",
  "Fixed interest rate index linked": "FIXED_RATE_INDEX_LINKED",
  "Prime interest rate": "PRIME_RATE",
  "Adjustable interest rate 2+2": "ADJUSTABLE_RATE_X2_NON_INDEXED",
  "Adjustable interest rate 2+2 index linked":
    "ADJUSTABLE_RATE_X2_INDEX_LINKED",
  "Adjustable interest rate 5+5": "ADJUSTABLE_RATE_X5_NON_INDEXED",
  "Adjustable interest rate 5+5 index linked":
    "ADJUSTABLE_RATE_X5_INDEX_LINKED",
};

const initialZeroMarginRates = {
  "0-59": 0,
  "60-119": 0,
  "120-179": 0,
  "180-239": 0,
  "240-299": 0,
  "300-359": 0,
  360: 0,
};

export default function SetZeroMarginRatesForm({ availableLoanTypes }) {
  const token = useSelector((state) => state.auth.token);

  const [selectedLoanType, setSelectedLoanType] = useState("");
  const [zeroMarginRates, setZeroMarginRates] = useState(
    initialZeroMarginRates
  );

  const sortedRates = Object.entries(zeroMarginRates).sort((a, b) => {
    const getValue = (key) => {
      const parts = key.split("-");
      return parts.length === 1 ? 360 : parseInt(parts[0]);
    };
    return getValue(a[0]) - getValue(b[0]);
  });

  const handleRateChange = (e, key) => {
    setZeroMarginRates({
      ...zeroMarginRates,
      [key]: parseFloat(e.target.value) || 0,
    });
  };

  const handleZeroMarginsSubmit = async (event) => {
    event.preventDefault();

    const loanTypeDto = new LoanRateTypeDto(
      loanTypesMap[selectedLoanType],
      zeroMarginRates
    );

    try {
      const response = await fetch(SET_ZERO_MARGINS_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(loanTypeDto),
      });

      if (!response.ok) {
        // TODO
        throw new Error("");
      }

      setSelectedLoanType("");
      setZeroMarginRates(initialZeroMarginRates);
      const responseData = await response.json();
      // TODO
      console.log(responseData);
    } catch (error) {
      // TOOD
      console.error(error);
    }
  };

  return (
    <div className={classes["form-container"]}>
      <div className={classes["loan-type-selection"]}>
        <p className={classes["loan-type-label"]}>Select Loan Type:</p>
        <div>
          <select
            className={classes["rate-select"]}
            onChange={(e) => setSelectedLoanType(e.target.value)}
          >
            {availableLoanTypes.map((l) => (
              <option key={l.id} value={l.loanType}>
                {l.loanType}
              </option>
            ))}
          </select>
        </div>
      </div>
      <form
        className={classes["rate-inputs"]}
        onSubmit={handleZeroMarginsSubmit}
      >
        {sortedRates.map(([key, value]) => (
          <div key={key} className={classes["rate-input-container"]}>
            <label className={classes["rate-label"]} htmlFor={key}>
              {`${key} months`}:{" "}
            </label>
            <input
              className={classes["rate-input"]}
              type="number"
              id={key}
              value={value}
              onChange={(e) => handleRateChange(e, key)}
              step="0.01"
            />
          </div>
        ))}
        <button type="submit" className={classes["submit-button"]}>
          Submit Loan Type
        </button>
      </form>
    </div>
  );
}

SetZeroMarginRatesForm.propTypes = {
  availableLoanTypes: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      loanType: PropTypes.string.isRequired,
    })
  ).isRequired,
};
