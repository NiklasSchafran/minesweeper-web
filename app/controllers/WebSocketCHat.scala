package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.libs.json._
import org.apache.pekko.actor._
import org.apache.pekko.stream.Materializer
import play.api.mvc._
import play.api.libs.streams.ActorFlow


@Singleton
class WebSocketChat @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
    def index() = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.chatPage())
    }
    def socket = WebSocket.accept[String, String] { request =>
        println("Getting sockets")
        ActorFlow.actorRef { out => 
            ???
        }    
    }
}