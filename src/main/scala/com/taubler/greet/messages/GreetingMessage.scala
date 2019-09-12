package com.taubler.greet.messages

import com.twitter.util.Future
import util.Random.nextInt

case class GreetingMessage(message: String, isQuestion: Boolean, name: Option[String] = None) {

  def withName(name: String) = GreetingMessage(this.message, this.isQuestion, Some(name))

}

object GreetingMessage {

  def apply(message: String, isQuestion: Boolean, name: Option[String] = None): GreetingMessage =
    new GreetingMessage(message, isQuestion, name)

  def obtain(): Future[GreetingMessage] =
    Future.value(nextInt(5) match {
      case 0 => GreetingMessage("Welcome", false)
      case 1 => GreetingMessage("So nice to see you", false)
      case 2 => GreetingMessage("Greetings", false)
      case 3 => GreetingMessage("How are you", true)
      case 4 => GreetingMessage("How have you been", true)
    })

  def obtain(name: String): Future[GreetingMessage] = obtain().map(g => g.withName(name))

}