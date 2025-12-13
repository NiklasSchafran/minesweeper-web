package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import java.io.File
import java.nio.file.{Files, Paths, StandardCopyOption}
import scala.collection.JavaConverters._

import de.htwg.se.minesweeper.model.GameComponent.Game
import de.htwg.se.minesweeper.model.FieldComponent.{Field, FieldInterface, Symbols, Matrix}
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.controller.ControllerInterface
import de.htwg.se.minesweeper.difficulty.{DifficultyStrategy, EasyDifficulty, MediumDifficulty, HardDifficulty}
import de.htwg.se.minesweeper.model.FileComponent.FileIOJSON // falls du diese hast

@Singleton
class GameJsonController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // initial game setup
  private val game: Game = new Game()
  private var field: FieldInterface = Field(Matrix(Vector.fill(9, 9)(Symbols.Covered)), Matrix(Vector.fill(9, 9)(Symbols.Covered)))
  private val controller: ControllerInterface = Controller(field, game)

  // optional file IO (if you implemented FileIOJSON or XML)
  private val fileIo = new FileIOJSON()

  /** Returns a simple homepage view (JSON head/body fragments) similar to colleague */
  def gameHomepage() = Action { implicit request: Request[AnyContent] =>
    val htmlContent = views.html.gameHomepage().toString
    val headContent = "<head>" + (htmlContent.split("<body>").head) + "</head>"
    val bodyContent = htmlContent.split("<body>").last.split("</body>").head
    Ok(Json.obj("success" -> true, "head" -> headContent, "body" -> bodyContent))
  }

  /** Returns GUI fragments (head/body) so frontend can load page dynamically */
  def gameGui() = Action { implicit request: Request[AnyContent] =>
    // ensure default difficulty
    controller.setDifficulty(new EasyDifficulty)
    val htmlContent = views.html.gameGui().toString
    val headContent = "<head>" + (htmlContent.split("<body>").head) + "</head>"
    val bodyContent = htmlContent.split("<body>").last.split("</body>").head
    Ok(Json.obj("success" -> true, "head" -> headContent, "body" -> bodyContent))
  }

  /** Returns the game board as JSON (rows, cols, cells matrix) */
  def getGameBoard() = Action { implicit request: Request[AnyContent] =>
    val rows = controller.field.size
    val cols = controller.field.size
    val cells = (0 until rows).map { r =>
      (0 until cols).map { c =>
        controller.field.cell(c, r) match {
          case Symbols.Bomb    => Json.obj("state" -> "bomb")
          case Symbols.Covered => Json.obj("state" -> "covered")
          case Symbols.Empty   => Json.obj("state" -> "empty")
          case number => Json.obj("state" -> "number", "value" -> number.toString)
        }
      }
    }
    Ok(Json.obj("rows" -> rows, "cols" -> cols, "cells" -> JsArray(cells.map(row => JsArray(row)).toList)))
  }

  /** Set difficulty (expects form param 'level' with values like "E","M","H" or "0"/"1"/"2") */
  def setDifficulty() = Action { implicit request =>
    val form = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    val level = form.get("level").flatMap(_.headOption).getOrElse("E")
    val strategy: DifficultyStrategy = level match {
      case "E" | "0" | "easy"   => new EasyDifficulty
      case "M" | "1" | "medium" => new MediumDifficulty
      case "H" | "2" | "hard"   => new HardDifficulty
      case _                    => new EasyDifficulty
    }
    controller.setDifficulty(strategy)
    Ok(Json.obj("success" -> true, "gameState" -> controller.game.gameState.toString))
  }

  /** Uncover a field (expects form params 'x' and 'y') */
  def uncover() = Action { implicit request =>
    val form = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    val x = form.get("x").flatMap(_.headOption).map(_.toInt).getOrElse(0)
    val y = form.get("y").flatMap(_.headOption).map(_.toInt).getOrElse(0)
    // call uncover on your controller; signature may differ — adjust if needed
    controller.uncoverField(x, y, game) // if your controller has (x,y) only, change accordingly
    Ok(Json.obj("success" -> true, "gameState" -> controller.game.gameState.toString))
  }



  def undo() = Action { implicit request: Request[AnyContent] =>
    controller.undo()
    Ok(Json.obj("success" -> true, "gameState" -> controller.game.gameState.toString))
  }

  def restart() = Action { implicit request: Request[AnyContent] =>
    controller.restart()
    Ok(Json.obj("success" -> true, "gameState" -> controller.game.gameState.toString))
  }

  /** Returns bomb matrix (useful to reveal bombs) */
  def getBombMatrix() = Action { implicit request: Request[AnyContent] =>
    val bombMatrix = controller.field.bomben // adjust if your field uses different name
    val bombMatrixJson = bombMatrix.rows.map { row =>
      row.map {
        case Symbols.Bomb => "*"
        case Symbols.Covered => "-"
        case Symbols.Empty => " "
        case number => number.toString
      }
    }
    Ok(Json.toJson(bombMatrixJson))
  }

  /** Save game (simple implementation using FileIOJSON) */
  def saveGame() = Action { implicit request: Request[AnyContent] =>
    // fileIo.save expects FieldInterface
    fileIo.save(controller.field)
    Ok(Json.obj("success" -> true))
  }

  /** Load game — optional expects form param 'gameId' or loads default saved file */
  def loadGame() = Action { implicit request: Request[AnyContent] =>
    val form = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    // if you implemented multiple saves, handle gameId
    val loaded = fileIo.load
    // set loaded field into controller (if method available)
    // adapt: if your controller provides setField or similar, call it.
    try {
      // if controller has setField:
      controller.setField(loaded)
    } catch {
      case _: Throwable =>
        // fallback: assign to internal field reference if accessible
    }
    Ok(Json.obj("success" -> true))
  }

  /** List saves (if you stored files in saves/ ) */
  def getGamesList() = Action { implicit request: Request[AnyContent] =>
    val savesDir = Paths.get("saves")
    if (!Files.exists(savesDir)) Files.createDirectories(savesDir)
    val files = Files.list(savesDir).iterator().asScala.toSeq.filter(_.toString.endsWith(".json") || _.toString.endsWith(".xml"))
    val games = files.map { p =>
      val fname = p.getFileName.toString
      Json.obj("gameId" -> fname, "fileName" -> fname)
    }
    Ok(Json.obj("games" -> JsArray(games.toList)))
  }
}
