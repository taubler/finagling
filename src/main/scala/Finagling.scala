import com.twitter.app.App
import com.twitter.finagle.Http
import com.twitter.finagle.Thrift.Server
import com.twitter.util.Await

object Finagling extends App {

  var default = 8080
  val port = flag[Int]("port", default, "port this server should listen on")

  def main(): Unit = {
//    runEasyServer()
    runScroogyServer()
  }

  def runScroogyServer(): Unit = {
    val serviceImpl = new GreetServerMepImpl()
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
