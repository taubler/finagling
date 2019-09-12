import com.taubler.greet.server.{EasyGreetServer, ScroogyMpeGreetServer, ScroogySpeGreetServer}
//import com.taubler.greet.{GreetServerSpeImpl, GreetService}
import com.twitter.app.App

object Finagling extends App {

  val scroogySpeServer = "SCROOGY_SPE"
  val scroogyMpeServer = "SCROOGY_MPE"
  val easyServer = "EASY"

  var default = 8080
  val port = flag[Int]("port", default, "port this server should listen on")
  val profile = scroogySpeServer

  def main(): Unit = {
    val server = profile match {
      case scroogySpeServer => new ScroogySpeGreetServer
      case scroogyMpeServer => new ScroogyMpeGreetServer
      case easyServer => new EasyGreetServer
      case other => throw new Exception(f"No valid profile: $other")
    }
    server.run(port.get.getOrElse(default))
  }

}
