import com.taubler.greet.thrift.{FullName, GreetingService}
import com.taubler.greet.thrift.GreetingService.{BeGreeted, BeUngreeted}
import com.twitter.finagle.Service
import com.twitter.util.Future

class GreetServerSpeImpl extends GreetingService.ServicePerEndpoint {

  override def beGreeted: Service[BeGreeted.Args, String] = {
    new Service[BeGreeted.Args, String] {
      override def apply(request: BeGreeted.Args): Future[String] = {
        Future.value(f"Hello, ${request.name.firstName} ${request.name.lastName}")
      }
    }
  }

  override def beUngreeted: Service[BeUngreeted.Args, String] = {
    new Service[BeUngreeted.Args, String] {
      override def apply(request: BeUngreeted.Args): Future[String] = {
        Future.value(f"Goodbye, ${request.name.firstName} ${request.name.lastName}. Have fun in ${request.newLocation}!")
      }
    }
  }

}
