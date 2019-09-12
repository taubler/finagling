package com.taubler.greet.service

import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finagle.{Service, http}
import com.twitter.util.Future

class GreetService extends Service[http.Request, http.Response] {

  override def apply(request: Request): Future[Response] = {
    request.method match {

      case Method.Get =>
        request.path match {

          case "/" =>
            val resp = http.Response(request.version, http.Status.Ok)
            resp.contentString = "Good job!"
            Future.value(resp)
          case "/square" =>
            val number = request.getIntParam("num")
            val resp = http.Response(request.version, http.Status.Ok)
            resp.contentString = s"${number * number}"
            Future.value(resp)
          case "/help" =>
            val resp = http.Response(request.version, http.Status.Ok)
            resp.contentString = "Go to the '/' URL to receive some feedback"
            Future.value(resp)
          case _ =>
            Future.value(http.Response(request.version, http.Status.NotFound))

        }
    }

  }

}
