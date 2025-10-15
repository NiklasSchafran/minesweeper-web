error id: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala:
file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 316
uri: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/app/controllers/HomeController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.controller.ControllerInterface
import de.htwg.se.minesweeper.model.GameComponent.game
import de.htwg.se.minesweeper.model.GameComponent.Status.Status}
import de.@@htwg.se.minesweeper.model.FieldComponent.{Field, FieldInterface, Symbols, Matrix}

/**
 * Einfacher Controller ohne Guice Injection
 */
@Singleton
class MinesweeperWebController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Minesweeper-Spiel direkt erstellen (ohne DI)
  private val game: Game = new Game(Status.Playing)
  private val field: FieldInterface = Field(Matrix(Vector.fill(10, 10)(Symbols.Covered)), Matrix(Vector.fill(10, 10)(Symbols.Covered)))
  private val controller: ControllerInterface = Controller(field, game)

  def index = Action {
    Ok("Welcome to Minesweeper Web! Use /state or /uncover/x/y")
  }

  def state = Action {
    Ok(controller.toString)
  }

  def uncover(x: Int, y: Int) = Action {
    controller.uncoverField(x, y, game)
    Ok(controller.toString)
  }

  def undo = Action {
    controller.undo()
    Ok(controller.toString)
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 