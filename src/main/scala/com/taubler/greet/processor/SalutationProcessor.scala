package com.taubler.greet.processor

import com.twitter.util.Future
import com.taubler.greet.thrift.Salutation

class SalutationProcessor {

  def process(salutaion: Salutation) : Future[String] = {
    Future.value(salutaion match {
      case Salutation.Dr => "Doctor"
      case Salutation.Mr => "Mister"
      case Salutation.Mrs => "Missus"
      case Salutation.Ms => "Miz"
    })
  }

}

object SalutationProcessor {

  def apply(): SalutationProcessor = new SalutationProcessor()
  
}