package com.taubler.greet.server

import com.taubler.greet.service.GreetServiceSpeImpl
import com.twitter.finagle.Filter.TypeAgnostic
import com.twitter.finagle.{Filter, Service, SimpleFilter}
import com.twitter.finagle.Thrift.Server
import com.twitter.util.{Await, Future}

class ScroogySpeGreetServer extends GreetServer {

  override def run(port: Int): Unit = {
    val serviceImpl = new GreetServiceSpeImpl()

    val filteredServiceImpl = serviceImpl.filtered(new TypeAgnostic {
      override def toFilter[Req, Rep]: Filter[Req, Rep, Req, Rep] = {
        new SimpleFilter[Req, Rep] {
          var x = 0
          override def apply(request: Req, service: Service[Req, Rep]): Future[Rep] = {
            println(f"The filter executed: $x")
            x += 1
            service(request)
          }
        }
      }
    })

    val methodPerEndpoint = filteredServiceImpl.toThriftService

    val server = new Server()
    val listeningServer = server.serveIface(s":$port", methodPerEndpoint)
    Await.ready(listeningServer)
  }

}
