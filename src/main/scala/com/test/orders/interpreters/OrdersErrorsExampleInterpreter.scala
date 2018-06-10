package com.test.orders.interpreters

import cats.~>
import com.test.orders.dsl.OrdersExampleDSL._

import scala.util.Left

object OrdersErrorsExampleInterpreter {
  def etheirInterpreter: Orders ~> ErrorOr =
    new (Orders ~> ErrorOr) {
      def apply[A](fa: Orders[A]): ErrorOr[A] = fa match {
        case Buy(stock, amount) =>
          Right(s"""$stock - $amount""")
        case Sell(stock, amount) =>
          Left(s"""Why are you selling that? $stock""")
      }
    }
}