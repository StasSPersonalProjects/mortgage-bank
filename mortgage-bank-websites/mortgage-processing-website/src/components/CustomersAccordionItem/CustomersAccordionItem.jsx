/* eslint-disable react/prop-types */
import { useDispatch } from "react-redux";
import AccordionItem from "../AccordionItem/AccordionItem";
import { workFieldActions } from "../../store/workField";
import classes from "./CustomersAccordionItem.module.css";

export default function CustomersAccordionItem({ title, data, dataField }) {
  const maritalStatusValues = {
    SINGLE: "Single",
    MARRIED: "Married",
    DIVORCED: "Divorced",
    WIDOWER: "Widower",
  };

  const employmentTypeValues = {
    HIRED_EMPLOYEE: "Hired employee",
    BUSINESS_OWNER: "Business owner",
    COMPANY_OWNER: "Company owner",
    UNEMPLOYED: "Unemployed",
  };

  const spendingValues = {
    RENT: "Rent",
    CHILD_SUPPORT: "Child support",
    LOAN: "Loan",
    MORTGAGE: "Mortgage",
    OTHER: "Other",
  };

  const extraIncomeValues = {
    RENT: "Rent",
    CHILD_SUPPORT: "Child support",
    ALLOWANCE: "Allowance",
    DISABILITY_ALLOWANCE: "Disability allowance",
    PENSION: "Pension",
    OTHER: "Other",
  };

  const dispatch = useDispatch();

  const handleMortgageRequestDataUpdate = (event, path) => {
    const value = event.target.value;
    dispatch(
      workFieldActions.updateMortgageRequestCustomerData({
        path,
        value,
      })
    );
  };

  return (
    <AccordionItem title={title}>
      <div className={classes["buttons"]}>
        <button>Add +</button>
        <button>Save</button>
      </div>
      {data[dataField].map((b, index) => (
        <AccordionItem
          key={b.identityCardNumber}
          title={`${b.firstName} ${b.lastName}`}
        >
          <div className={classes["customer-data-accordion"]}>
            <div className={classes["customer-personal-data"]}>
              <div className={classes["label-input"]}>
                <label htmlFor="id-card-number">ID card number:</label>
                <input
                  type="text"
                  id="id-card-number"
                  value={b.identityCardNumber}
                  disabled={b.identityCardNumber ? true : false}
                  onChange={
                    !b.identityCardNumber
                      ? (e) =>
                          handleMortgageRequestDataUpdate(
                            e,
                            `${dataField}[${index}].identityCardNumber`
                          )
                      : null
                  }
                />
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="first-name">First name:</label>
                <input
                  type="text"
                  id={`first-name-${index}`}
                  value={b.firstName}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].firstName`
                    )
                  }
                />
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="last-name">Last name:</label>
                <input
                  type="text"
                  id={`last-name-${index}`}
                  value={b.lastName}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].lastName`
                    )
                  }
                />
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="birth-date">Birth date:</label>
                <input
                  type="date"
                  id="birth-date"
                  value={b.birthDate}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].birthDate`
                    )
                  }
                />
              </div>
            </div>
            <div className={classes["customer-personal-data"]}>
              <div className={classes["label-input"]}>
                <label htmlFor="phone-number">Phone number:</label>
                <input
                  type="text"
                  id="phone-number"
                  value={b.phoneNumber}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].phoneNumber`
                    )
                  }
                />
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="email-address">E-mail:</label>
                <input
                  type="text"
                  id="email-address"
                  value={b.email}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].email`
                    )
                  }
                />
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="marital-status">Marital status:</label>
                <input
                  type="text"
                  id="marital-status"
                  value={maritalStatusValues[b.maritalStatus]}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].maritalStatus`
                    )
                  }
                  list="marital-status-values"
                />
                <datalist id="marital-status-values">
                  {Object.values(maritalStatusValues).map((v) => (
                    <option key={v} value={v} />
                  ))}
                </datalist>
              </div>
              <div className={classes["label-input"]}>
                <label htmlFor="children-under-21">
                  Children under age 21:
                </label>
                <input
                  type="number"
                  id="children-under-21"
                  value={b.childrenUnderAge21}
                  onChange={(e) =>
                    handleMortgageRequestDataUpdate(
                      e,
                      `${dataField}[${index}].childrenUnderAge21`
                    )
                  }
                />
              </div>
            </div>
          </div>
          <div className={classes["income-header"]}>
            <p>Income Sources</p>
            <button>Add income source +</button>
          </div>
          {b.employmentData.map((e, innerIndex) => (
            <div
              key={e.placeOfEmployment}
              className={classes["income-outcome"]}
            >
              <div className={classes["customer-income-data"]}>
                <div className={classes["label-input"]}>
                  <label htmlFor="employemnt-type">Employment type:</label>
                  <input
                    type="text"
                    id="employemnt-type"
                    list="employment-type-values"
                    defaultValue={employmentTypeValues[e.employmentType]}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].employmentType`
                      )
                    }
                  />
                  <datalist id="employment-type-values">
                    {Object.values(employmentTypeValues).map((v) => (
                      <option key={v} value={v} />
                    ))}
                  </datalist>
                </div>
                <div className={classes["label-input"]}>
                  <label htmlFor="employment-place">Place of employment:</label>
                  <input
                    type="text"
                    id="employment-place"
                    defaultValue={e.placeOfEmployment}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].placeOfEmployment`
                      )
                    }
                  />
                </div>
                <div className={classes["label-input"]}>
                  <label htmlFor="position">Position</label>
                  <input
                    type="text"
                    id="position"
                    defaultValue={e.position}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].position`
                      )
                    }
                  />
                </div>
              </div>
              <div className={classes["customer-income-data"]}>
                <div className={classes["label-input"]}>
                  <label htmlFor="duration-in-years">
                    {"Duration (years):"}
                  </label>
                  <input
                    id="duration-in-years"
                    type="number"
                    step="0.1"
                    defaultValue={e.durationOfEmploymentInYears}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].durationOfEmploymentInYears`
                      )
                    }
                  />
                </div>
                <div className={classes["label-input"]}>
                  <label htmlFor="monthly-salary">Monthly salary:</label>
                  <input
                    id="monthly-salary"
                    type="number"
                    step="100.0"
                    defaultValue={e.monthlySalary}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].monthlySalary`
                      )
                    }
                  />
                </div>
                <div className={classes["label-input"]}>
                  <label htmlFor="yearly-salary">Yearly salary:</label>
                  <input
                    id="yearly-salary"
                    type="number"
                    step="1000.0"
                    defaultValue={e.yearlySalary}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].employmentData[${innerIndex}].yearlySalary`
                      )
                    }
                  />
                </div>
              </div>
              <button className={classes["remove-income-btn"]}>Remove</button>
            </div>
          ))}
          <div className={classes["spendings-header"]}>
            <p>Spendings</p>
            <button>Add spending +</button>
          </div>
          {b.spendings.map((s, innerIndex) => (
            <div key={innerIndex} className={classes["spendings"]}>
              <div className={classes["customer-spendings-data"]}>
                <div className={classes["spending-label-input"]}>
                  <label htmlFor="spending-type">Type:</label>
                  <input
                    type="text"
                    id="spending-type"
                    defaultValue={spendingValues[s.spendingType]}
                    list="spending-values"
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].spendings[${innerIndex}].spendingType`
                      )
                    }
                  />
                  <datalist id="spending-values">
                    {Object.values(spendingValues).map((v) => (
                      <option key={v} value={v} />
                    ))}
                  </datalist>
                </div>
                <div className={classes["spending-label-input"]}>
                  <label htmlFor="spending-amount">Amount:</label>
                  <input
                    type="number"
                    id="spending-amount"
                    defaultValue={s.spendingAmount}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].spendings[${innerIndex}].spendingAmount`
                      )
                    }
                  />
                </div>
                <div className={classes["spending-label-input"]}>
                  <label htmlFor="remaining-duration">
                    {"Duration (months):"}
                  </label>
                  <input
                    type="number"
                    id="remaining-duration"
                    defaultValue={s.remainingDurationInMonths}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].spendings[${innerIndex}].remainingDurationInMonths`
                      )
                    }
                  />
                </div>
              </div>
              <button className={classes["remove-spending-btn"]}>Remove</button>
            </div>
          ))}
          <div className={classes["extra-income-header"]}>
            <p>Extra Income</p>
            <button>Add extra income +</button>
          </div>
          {b.extraIncomes.map((e, innerIndex) => (
            <div key={innerIndex} className={classes["extra-incomes"]}>
              <div className={classes["customer-extra-income-data"]}>
                <div className={classes["extra-income-label-input"]}>
                  <label htmlFor="extra-income-type">Type:</label>
                  <input
                    type="text"
                    id="extra-income-type"
                    list="extra-income-values"
                    defaultValue={extraIncomeValues[e.incomeType]}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].extraIncomes[${innerIndex}].incomeType`
                      )
                    }
                  />
                  <datalist id="extra-income-values">
                    {Object.values(extraIncomeValues).map((v) => (
                      <option key={v} value={v} />
                    ))}
                  </datalist>
                </div>
                <div className={classes["extra-income-label-input"]}>
                  <label htmlFor="extra-income-amount">Amount:</label>
                  <input
                    type="number"
                    step="50.0"
                    id="extra-income-amount"
                    defaultValue={e.amount}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].extraIncomes[${innerIndex}].amount`
                      )
                    }
                  />
                </div>
                <div className={classes["extra-income-label-input"]}>
                  <label htmlFor="extra-income-description">Description:</label>
                  <input
                    type="text"
                    id="extra-income-description"
                    defaultValue={e.description}
                    onBlur={(e) =>
                      handleMortgageRequestDataUpdate(
                        e,
                        `${dataField}[${index}].extraIncomes[${innerIndex}].description`
                      )
                    }
                  />
                </div>
              </div>
              <button className={classes["remove-extra-income-btn"]}>
                Remove
              </button>
            </div>
          ))}
          <div className={classes["customer-extra-info"]}>
            <label htmlFor="customer-extra-info">Extra info:</label>
            <textarea
              type="text"
              id="customer-extra-info"
              rows={1}
              maxLength={100}
              defaultValue={b.extraInfo}
              onBlur={(e) =>
                handleMortgageRequestDataUpdate(
                  e,
                  `${dataField}[${index}].extraInfo`
                )
              }
            ></textarea>
          </div>
          <button>Remove customer</button>
        </AccordionItem>
      ))}
    </AccordionItem>
  );
}
