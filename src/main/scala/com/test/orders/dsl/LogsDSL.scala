package com.test.orders.dsl

import cats.free.Free
import cats.implicits._
import cats.{Id, Inject, ~>}
import com.test.orders.dsl.OrdersExampleDSL.{OrderI, Orders, Response}
import scalaz.Coproduct

object LogsDSL {
  sealed trait Log[A]

  case class Info(msg: String) extends Log[Unit]

  case class Error(msg: String) extends Log[Unit]

  class LogI[F[_]](implicit I: Inject[Log, F]){
    def infoI(msg: String): Free[F, Unit] =
      Free.inject[Log, F](Info(msg))

    def errorI(msg: String): Free[F, Unit] =
      Free.inject[Log, F](Error(msg))
  }

  implicit def logI[F[_]](implicit I: Inject[Log, F]): LogI[F] = new LogI[F]

  type TradeApp[A] = Coproduct[Orders, Log, A]

  def smartTradeWithLogs(implicit OR: OrderI[TradeApp],
                         LG: LogI[TradeApp]
  ): Free[TradeApp, Response] = {

    import LG._
    import OR._

    for{
      _ <- infoI("I am going to trade smartly")
      _ <- buyI("APPL", 100)
      _ <- infoI("I am going to trade even smartly")
      _ <- buyI("MSFT", 100)
      rsp <- sellI("GOOG", 100)
      _ <- errorI("Wait wait?!")
    }yield rsp
  }

}
