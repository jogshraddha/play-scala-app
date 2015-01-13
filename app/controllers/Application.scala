package controllers

import play.api._
import play.api.mvc._

object Application extends UserController {

  def index = Action {
    Ok(views.html.index("'Your new application is ready."))
  }

  def register =  Action(parse.json) { implicit request =>
    Ok("Registered")
  }
  

  def update(name : String) = TODO

}
