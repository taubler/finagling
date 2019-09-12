package com.taubler.greet

import com.taubler.greet.service.GreetServiceSpeImpl

import collection.mutable.Stack
import org.scalatest.{FlatSpec, Matchers}
import com.taubler.greet.thrift.GreetingService.{BeGreeted, BeUngreeted}
import com.twitter.finagle.Service

class ExampleSpec extends FlatSpec with Matchers {

  "A stack" can "pop values in LIFO order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  "A stack" should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }

  "A GreetServerSpelImpl" should "return a proper service" in {
    val serverImpl = new GreetServiceSpeImpl()
    assert(serverImpl.beGreeted.isInstanceOf[Service[BeGreeted.Args, String]])
    assert(serverImpl.beUngreeted.isInstanceOf[Service[BeUngreeted.Args, String]])
  }

}
