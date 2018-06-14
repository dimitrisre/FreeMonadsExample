package com.test.orders

import com.test.orders.dsl.OrdersExampleDSL.{smartTraide,smartTraideWithList}
import com.test.orders.interpreters.OrdersIdExampleInterpreter.orderPrinter
import com.test.orders.interpreters.OrdersErrorsExampleInterpreter.etheirInterpreter
import com.test.orders.interpreters.OrdersLogExampleInterpreter.composedInterpreter
import com.test.orders.interpreters.OrdersLogExampleInterpreter.logPrinter
import com.test.orders.dsl.LogsDSL.smartTradeWithLogs
import com.test.orders.dsl.LogsDSL.logI
import com.test.orders.dsl.OrdersExampleDSL.orderI
import cats.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    smartTraide.foldMap(orderPrinter)
    smartTraideWithList.foldMap(orderPrinter)

    val a = smartTraide.foldMap(etheirInterpreter)
    a match {
      case Left(error) => println(s"The error string is: $error")
      case Right(msg) => println(s"The message is: $msg")
    }

    smartTradeWithLogs.foldMap(composedInterpreter)
  }
}
