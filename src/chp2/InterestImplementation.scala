package chp2

object InterestTaxCalculationTable extends TaxCalculationTable {
  type T = TransactionType
  val transactionType = InterestComputation
}
object TaxCalculation extends TaxCalculation {
  type S = TaxCalculationTable
  val table = InterestTaxCalculationTable
}
object InterestCalculation extends InterestCalculation {
  type C = TaxCalculation
  val taxCalculation = TaxCalculation
}
