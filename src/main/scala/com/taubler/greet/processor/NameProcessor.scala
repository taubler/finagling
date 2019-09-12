package com.taubler.greet.processor

import com.taubler.greet.thrift.FullName
import com.twitter.util.Future

class NameProcessor {

  lazy val salutationProcessor = SalutationProcessor()

  def process(name: FullName) : Future[String] = {
    name.salutation.fold(Future.value(""))(salutationProcessor.process(_))
      .map {
        sal => Array(sal, name.firstName, name.lastName, name.suffix.getOrElse("")).filter(_.length > 0).foldLeft("")(_ + " " + _)
      }
  }

}

object NameProcessor {

  def apply(): NameProcessor = new NameProcessor()

}
