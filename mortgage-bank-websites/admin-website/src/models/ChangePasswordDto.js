export default class ChangePasswordDto {
  constructor(currentPassword, newPassword, confirmationPassword) {
    this.currentPassword = currentPassword;
    this.newPassword = newPassword;
    this.confirmationPassword = confirmationPassword;
  }
}
