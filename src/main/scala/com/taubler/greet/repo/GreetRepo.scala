package com.taubler.greet.repo

import java.util.UUID

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql.Result
import com.twitter.util.Future

class GreetRepo {

  val mysqlUsername = System.getenv("MYSQL_USER")
  val mysqlPassword = System.getenv("MYSQL_PWD")
  val mysqlDatabase = System.getenv("MYSQL_DB")
  val mysqlHost = System.getenv("MYSQL_HOST")

  val client = Mysql.client
    .withCredentials(mysqlUsername, mysqlPassword)
    .withDatabase(mysqlDatabase)
    .newRichClient(s"$mysqlHost:3306")

  def save(greeting: String): Future[String] = {
    val ps = client.prepare("INSERT INTO GREETING (GUID, MESSAGE) values (?, ?)");
    ps.apply(UUID.randomUUID().toString.toUpperCase(), greeting).map(r => greeting)
  }

}
