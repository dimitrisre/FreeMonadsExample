package com.test.orders.dsl

import cats.free.Free
import cats.free.Free._
import cats.implicits._

import scala.util.Either

object OrdersExampleDSL {
  type ErrorOr[A] = Either[String, A]

  type Symbol = String
  type Response = String

  sealed trait Orders[A]

  case class Buy(stock: Symbol, amount: Int) extends Orders[Response]
  case class Sell(stock: Symbol, amount: Int) extends Orders[Response]

  case class ListStocks() extends Orders[List[Symbol]]

  def listStocks(): OrdersF[List[Symbol]] =
    liftF[Orders, List[Symbol]](ListStocks())

  type OrdersF[A] = Free[Orders, A]

  def buy(stock: Symbol, amount: Int): OrdersF[Response] =
    liftF[Orders, Response](Buy(stock, amount))

  def sell(stock: Symbol, amount: Int): OrdersF[Response] =
    liftF[Orders, Response](Sell(stock, amount))

  def smartTraide: OrdersF[Response] = for{
    _ <- buy("APPL", 50)
    _ <- buy("MSFT", 100)
    rsp <- buy("GOOG3", 41)
    _ <- sell("GOOG", 40)
  } yield rsp

  def smartTraideWithList: Free[Orders, String] = for{
    lst <- listStocks()
    _ <- lst.traverse_(buy(_, 100))
    rsp <- sell("GOOG", 40)
  } yield rsp
}
