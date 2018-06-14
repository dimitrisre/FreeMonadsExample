package com.test.orders.dsl

import scalaz.Coproduct
import cats._
import cats.data._
import cats.free.Free._
import cats.free.Free
import cats.{Id, Inject, ~>}
import cats.implicits._

import com.test.orders.dsl.OrdersExampleDSL.{OrderI, Orders, Response}

object LogsDSL {
  trait Log[A]

  type TradeApp[A] = EitherK[Orders, Log, A]

  case class Info(msg: String) extends Log[Unit]

  case class Error(msg: String) extends Log[Unit]

  class LogI[F[_]](implicit I: InjectK[Log, F]){

    def infoI(msg: String): Free[F, Unit] = Free.inject[Log, F](Info(msg))

    def errorI(msg: String): Free[F, Unit] = Free.inject[Log, F](Error(msg))
  }

  implicit def logI[F[_]](implicit I: InjectK[Log, F]): LogI[F] = new LogI[F]


  def smartTradeWithLogs(implicit O: OrderI[TradeApp],
                         I: LogI[TradeApp]): Free[TradeApp, Response] = {

    import O._
    import I._

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
