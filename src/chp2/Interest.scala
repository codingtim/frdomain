package chp2

import chp2.common.{Amount, Balance}

sealed trait TaxType

case object Tax extends TaxType
case object Fee extends TaxType
case object Commission extends TaxType

sealed trait TransactionType
case object InterestComputation extends TransactionType
case object Dividend extends TransactionType

object common {
  type Amount = BigDecimal
  case class Balance(amount: Amount = 0)
}


trait TaxCalculationTable {
  type T <: TransactionType
  val transactionType: T

  def getTaxRates: Map[TaxType, Amount] = {
    //dummy
    Map.empty
  }
}

trait TaxCalculation {
  type S <: TaxCalculationTable
  val table: S

  def calculate(taxOn: Amount): Amount =
    table.getTaxRates.map { case (t, r) =>
      doCompute(taxOn, r)
    }.sum

  protected def doCompute(taxOn: Amount, rate: Amount): Amount = {
    taxOn * rate
  }
}

trait SingaporeTaxCalculation extends TaxCalculation {
  def calculateGST(tax: Amount, gstRate: Amount) =
    tax * gstRate
}

trait InterestCalculation {
  type C <: TaxCalculation
  val taxCalculation: C

  def interest(b: Balance): Option[Amount] = Some(b.amount * 0.05)

  def calculate(balance: Balance): Option[Amount] =
    interest(balance).map { i =>
      i - taxCalculation.calculate(i)
    }
}
