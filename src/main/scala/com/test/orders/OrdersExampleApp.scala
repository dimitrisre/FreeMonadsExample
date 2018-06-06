package com.test.orders

import com.test.orders.dsl.OrdersExampleDSL.{smartTraide,smartTraideWithList}
import com.test.orders.interpreters.OrdersIdExampleInterpreter.orderPrinter
import com.test.orders.interpreters.OrdersErrorsExampleInterpreter.etheirInterpreter
import cats.implicits._

object Main {
  def main(args: Array[String]) {
    smartTraide.foldMap(orderPrinter)
    smartTraideWithList.foldMap(orderPrinter)

    val a = smartTraide.foldMap(etheirInterpreter)
    a match {
      case Left(error) => println(s"The error string is: $error")
      case Right(msg) => println(s"The message is: $msg")
    }
  }
}
