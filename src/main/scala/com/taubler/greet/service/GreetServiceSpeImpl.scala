package com.taubler.greet.service

import com.taubler.greet.messages.GreetingMessage
import com.taubler.greet.processor.NameProcessor
import com.taubler.greet.repo.GreetRepo
import com.taubler.greet.thrift.GreetingService
import com.taubler.greet.thrift.GreetingService.{BeGreeted, BeUngreeted}
import com.twitter.finagle.Service
import com.twitter.util.Future

class GreetServiceSpeImpl extends GreetingService.ServicePerEndpoint {

  val nameProcessor = NameProcessor()

  val greetRepo = new GreetRepo()

  override def beGreeted: Service[BeGreeted.Args, String] = {
    new Service[BeGreeted.Args, String] {
      override def apply(request: BeGreeted.Args) =
        nameProcessor.process(request.name).flatMap { n =>
          GreetingMessage.obtain(n)
        } flatMap { g =>
          val msg =  g.message + g.name.getOrElse("") + (if (g.isQuestion) { "?" } else { "!" })
          greetRepo.save(msg)
        }
    }
  }

  override def beUngreeted: Service[BeUngreeted.Args, String] = {
    (request: BeUngreeted.Args) => nameProcessor.process(request.name).map { n =>
      f"Goodbye, $n. Have fun in ${request.newLocation}!"
    }
  }

}
