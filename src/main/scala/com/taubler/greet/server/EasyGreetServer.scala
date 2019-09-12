package com.taubler.greet.server

import com.taubler.greet.service.GreetService
import com.twitter.finagle.{Http, Service, SimpleFilter, http}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.{Await, Future}

class EasyGreetServer extends GreetServer {

  override def run(port: Int): Unit = {
    println(s"Will start server on port ${port}")

    val service = new GreetService()
    val mungeFilter: SimpleFilter[http.Request, http.Response] = new SimpleFilter[http.Request, http.Response] {
      override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
        println("The filter executed")
        service(request)
      }
    }

    val mungedService = mungeFilter andThen service

    val server = Http.serve(s":$port", mungedService)

    Await.ready(server)
  }

}
