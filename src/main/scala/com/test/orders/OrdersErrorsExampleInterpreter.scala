package com.test.orders

import cats.~>
import com.test.orders.OrdersExampleDSL.{Buy, ErrorOr, Orders, Sell}
import scala.util.{Either, Right, Left}

object OrdersErrorsExampleInterpreter {
  def etheirInterpreter: Orders ~> ErrorOr =
    new (Orders ~> ErrorOr){
      def apply[A](fa: Orders[A]): ErrorOr[A] = fa match {
        case Buy(stock, amount) =>
          Right(s"""$stock - $amount""")
        case Sell(stock, amount) =>
          Left("Why are you selling that?")
      }
    }
}
