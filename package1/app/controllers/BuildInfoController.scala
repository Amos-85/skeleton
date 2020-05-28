package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc._
import buildinfo.BuildInfo

@Singleton
class BuildInfoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def info = Action {
    Ok(Json.parse(BuildInfo.toJson))
  }
}