import java.net.SocketAddress

import com.taubler.greet.thrift.{FullName, GreetingService}
import com.twitter.app.App
import com.twitter.finagle.ssl.Protocols
import com.twitter.finagle.{Client, Http, ListeningServer, Service, http}
import com.twitter.scrooge.ThriftExceptionResponse
import com.twitter.util.{Await, Awaitable, Duration, Future, Time}
import com.twitter.finagle.Thrift.Server

object Finagling extends App {

  var default = 8080
  val port = flag[Int]("port", default, "port this server should listen on")

  def main(): Unit = {
//    runEasyServer()
    runScroogyServer()
  }

  def runScroogyServer(): Unit = {
    val serviceImpl = new GreetServerImpl()
    val server = new Server()
    val listeningServer = server.serveIface(s":${port.get.getOrElse(default)}", serviceImpl)
    Await.ready(listeningServer)
  }

  def runEasyServer(): Unit = {
    println(s"Start server on port ${port}")

    val service = new GreetService()

    val server = Http.serve(s":${port.get.getOrElse(default)}", service)

    Await.ready(server)
  }

}
