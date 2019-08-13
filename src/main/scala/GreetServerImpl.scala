import com.taubler.greet.thrift.{FullName, GreetingService}
import com.twitter.util.Future

class GreetServerImpl extends GreetingService.MethodPerEndpoint {
  override def beGreeted(name: FullName): Future[String] = {
    Future.value(f"Hello, ${name.firstName} ${name.lastName}")
  }
}
