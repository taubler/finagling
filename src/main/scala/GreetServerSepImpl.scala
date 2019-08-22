import com.taubler.greet.thrift.GreetingService
import com.taubler.greet.thrift.GreetingService.BeGreeted
import com.twitter.finagle.Service
import com.twitter.util.Future

class GreetServerSepImpl extends GreetingService.ServicePerEndpoint {

  override def beGreeted: Service[BeGreeted.Args, String] = {
    Future.value(f"Hello, ${name.firstName} ${name.lastName}")
  }

}
