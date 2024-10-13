export default class LoanRateTypeDto {
  constructor(loanType, zeroMarginRates) {
    this.loanType = loanType;
    this.zeroMarginRates = zeroMarginRates;
  }
}
