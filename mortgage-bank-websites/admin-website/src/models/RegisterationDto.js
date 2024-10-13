export default class RegisterationDto {
  constructor(idCardNumber, firstName, lastName, roles) {
    this.idCardNumber = idCardNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
  }
}
