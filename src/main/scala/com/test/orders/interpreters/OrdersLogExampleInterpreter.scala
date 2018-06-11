package com.test.orders.interpreters

import cats.{Id, ~>}
import com.test.orders.dsl.LogsDSL._

object OrdersLogExampleInterpreter {
  def logPrinter: Log ~> Id =
    new (Log ~> Id){
      def apply[A](fa: Log[A]): Id[A] =
        fa match {
          case Info(msg) =>
            println(s"""[Info] - $msg""")
          case Error(msg) =>
            println(s"""[Error] - $msg""")
        }
    }
}
