export default class UserMortgageRequestData {
  constructor(
    id,
    firstName,
    lastName,
    dateOfBirth,
    phoneNumber,
    personalStatus,
    children, // object
    employment, // array of objects
    spendings // array of objects
  ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.personalStatus = personalStatus;
    this.children = children;
    this.employment = employment;
    this.spendings = spendings;
  }
}
