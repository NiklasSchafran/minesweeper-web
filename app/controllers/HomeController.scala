package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.controller.ControllerInterface
import de.htwg.se.minesweeper.model.GameComponent.Game
import de.htwg.se.minesweeper.model.GameComponent.Status
import de.htwg.se.minesweeper.model.FieldComponent.{Field, FieldInterface, Symbols, Matrix}
import de.htwg.se.minesweeper.difficulty.{DifficultyStrategy, EasyDifficulty, MediumDifficulty, HardDifficulty}

/**
 * Einfacher Controller ohne Guice Injection
 */
@Singleton
class MinesweeperWebController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Minesweeper-Spiel direkt erstellen (ohne DI)
  private val game: Game = new Game()
  private val field: FieldInterface = Field(Matrix(Vector.fill(10, 10)(Symbols.Covered)), Matrix(Vector.fill(10, 10)(Symbols.Covered)))
  private val controller: ControllerInterface = Controller(field, game)

  def index = Action {
    Ok("Welcome to Minesweeper Web! Use /difficulty/level or /uncover/x/y")
  }

  def state = Action {
    Ok(controller.toString)
  }

  def uncover(x: Int, y: Int) = Action {
    controller.uncoverField(x, y, game)
    Ok(controller.toString)
  }

  def setDifficulty(level: String) = Action {
    val strategy = level match {
      case "0" | "easy"   => new de.htwg.se.minesweeper.difficulty.EasyDifficulty
      case "1" | "medium" => new de.htwg.se.minesweeper.difficulty.MediumDifficulty
      case "2" | "hard"   => new de.htwg.se.minesweeper.difficulty.HardDifficulty
      case _              => new de.htwg.se.minesweeper.difficulty.EasyDifficulty
    }
    controller.setDifficulty(strategy)
    Ok(s"Difficulty set to $level\n${controller.toString}")
  }


  def undo = Action {
    controller.undo()
    Ok(controller.toString)
  }
}
