error id: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala:`<none>`.
file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1109
uri: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala
text:
```scala
package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Guice
import scala.concurrent.Future
import de.htwg.se.minesweeper.util.Observable
import de.htwg.se.minesweeper.model._
import de.htwg.se.minesweeper.model.GameComponent.*
import de.htwg.se.minesweeper.model.FieldComponent.*
import de.htwg.se.minesweeper.difficulty.DifficultyStrategy
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.controller.ControllerInterface
import de.htwg.se.minesweeper.MinesweeperModule

import javax.inject._
import play.api.mvc._
import de.htwg.se.minesweeper.controller.ControllerInterface

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

@Singleton
class HomeController @Inject()(
    cc: ControllerComponents,
    controller: ControllerInterface  // direkt injected
) extends Abstract@@Controller(cc) {  
  // Dein Spiel wird über Guice erzeugt, so wie in deiner Main-Klasse
  private val injector = Guice.createInjector(new MinesweeperModule)
  private val controller = injector.getInstance(classOf[ControllerInterface])

  def index = Action {
    Ok("Welcome to Minesweeper Web! Use /state or /uncover/x/y")
  }

  def state = Action {
    Ok(controller.toString)
  }

  def uncover(x: Int, y: Int) = Action {
    controller.uncoverField(x, y, controller.game)
    Ok(controller.toString)
  }

  def undo = Action {
    controller.undo()
    Ok(controller.toString)
  }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.