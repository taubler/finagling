package com.taubler.greet.server

import com.taubler.greet.service.GreetServiceMpeImpl
import com.twitter.finagle.Thrift.Server
import com.twitter.util.Await

class ScroogyMpeGreetServer extends GreetServer {

  override def run(port: Int): Unit = {
    val serviceImpl = new GreetServiceMpeImpl()
    val server = new Server()
    val listeningServer = server.serveIface(s":$port", serviceImpl)
    Await.ready(listeningServer)
  }

}
