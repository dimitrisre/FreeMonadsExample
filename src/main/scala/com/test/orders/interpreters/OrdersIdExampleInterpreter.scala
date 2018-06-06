package com.test.orders.interpreters

import cats.{Id, ~>}
import com.test.orders.dsl.OrdersExampleDSL.{Buy, ListStocks, Orders, Sell}

object OrdersIdExampleInterpreter {
  def orderPrinter: Orders ~> Id =
    new (Orders ~> Id) {
      def apply[A](fa: Orders[A]): Id[A] = fa match {
        case ListStocks() =>
          println("Listing stock")
          List("APPL","GOOG","MSC")
        case Buy(stock, amount) =>
          println(s"Buying $amount of $stock")
          "ok"
        case Sell(stock, amount) =>
          println(s"Sellin $amount of $stock")
          "ok"
      }
    }
}
