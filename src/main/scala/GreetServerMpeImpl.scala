import com.taubler.greet.thrift.{FullName, GreetingService}
import com.twitter.util.Future

class GreetServerMpeImpl extends GreetingService.MethodPerEndpoint {

  override def beGreeted(name: FullName): Future[String] = {
    Future.value(f"Hello, ${name.firstName} ${name.lastName}")
  }

  override def beUngreeted(name: FullName, newLocation: String): Future[String] = {
    Future.value(f"Goodbye, ${name.firstName} ${name.lastName}. Have fun in ${newLocation}!")
  }
}
