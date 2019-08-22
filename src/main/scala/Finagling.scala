import com.taubler.greet.thrift.FullName
import com.taubler.greet.thrift.GreetingService.{BeGreeted, BeUngreeted}
import com.twitter.app.App
import com.twitter.finagle.{Filter, Http, Service, SimpleFilter, http}
import com.twitter.finagle.Thrift.Server
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.{Await, Future}

object Finagling extends App {

  var default = 8080
  val port = flag[Int]("port", default, "port this server should listen on")

  def main(): Unit = {
    runEasyServer()
//    runScroogyMepServer()
//    runScroogySepServer()
  }

  def runScroogyMepServer(): Unit = {
    val serviceImpl = new GreetServerMpeImpl()
    val server = new Server()
    val listeningServer = server.serveIface(s":${port.get.getOrElse(default)}", serviceImpl)
    Await.ready(listeningServer)
  }

  def runScroogySepServer(): Unit = {
    val mungeFilter: SimpleFilter[BeGreeted.Args, String] = new SimpleFilter[BeGreeted.Args, String] {
      def apply(req: BeGreeted.Args, service: Service[BeGreeted.Args, String]): Future[String] = {
        println("The filter executed")
        val uppercaseRequest = req.copy(name = FullName("John", "Smith", "Sr"))
        service(uppercaseRequest)
      }
    }

    val serviceImpl = new GreetServerSpeImpl()

    val filteredServiceImpl = serviceImpl.withBeGreeted(mungeFilter.andThen(serviceImpl.beGreeted))

//    val methodPerEndpoint = serviceImpl.toThriftService
    val methodPerEndpoint = filteredServiceImpl.toThriftService

    val server = new Server()
    val listeningServer = server.serveIface(s":${port.get.getOrElse(default)}", methodPerEndpoint)
    Await.ready(listeningServer)
  }

  def runEasyServer(): Unit = {
    println(s"Will start server on port ${port}")

    val service = new GreetService()
    val mungeFilter: SimpleFilter[http.Request, http.Response] = new SimpleFilter[http.Request, http.Response] {
      override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
        println("The filter executed")
        service(request)
      }
    }

    val mungedService = mungeFilter andThen service

    val server = Http.serve(s":${port.get.getOrElse(default)}", mungedService)

    Await.ready(server)
  }

}
