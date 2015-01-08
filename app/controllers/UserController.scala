package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import domain.User

trait UserController extends Controller{
  val users = new ConcurrentHashMap[Long, User]
  val idSequence = new AtomicLong(0)
  
  def getUsers = Action {
    Ok("List of Users : "+users.toString)
  }
  
  def createUser =  Action(parse.json) { implicit request =>
    (request.body \ "name").asOpt[String].map { name =>
      val newId = idSequence.incrementAndGet()
      val user = User(newId, name)
      users.put(newId, user)
      Ok("Created " + user.toString)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }
  
  def findUserById(id : Long) = Action {
    val user = Option(users.get(id))
     if (user.isDefined) {
       Ok("Found : "+user.toString)
     } else {
       NotFound
     }
  }
  
  def deleteUser(id : Long) = Action {
    val user = Option(users.get(id))
    users.remove(id);
    NoContent
  }
}