package controllers

import forms.{AssignmentForm, LoginForm, ResetForm, UserForm}
import javax.inject.Inject
import modals.{AssignmentDbRepo, AssignmentRepo, DatabaseRepo, UserRepo}
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(controllerComponent: ControllerComponents,
                            userForm: UserForm,resetForm: ResetForm,loginForm:LoginForm,assignmentForm: AssignmentForm, dbRepo: DatabaseRepo,assignmentDbRepo:AssignmentDbRepo ) extends AbstractController(controllerComponent) {

  def index: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(views.html.main()))
  }

  def signUpIndex: Action[AnyContent] = Action.async{ implicit request=>
    Future.successful(Ok(views.html.signUp(userForm.userForm)))

  }

  def signUp: Action[AnyContent] = Action.async { implicit request =>

    userForm.userForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(s"${formWithError.errors}"))
      },
      data => {
        dbRepo.get(data.username).flatMap {
          optionalUser =>
            optionalUser.fold {
              val dbPayload: UserRepo= UserRepo(0,data.userType,data.firstname,data.middlename,data.lastname,data.username,
                data.password.password,data.phoneNo,data.gender,data.age,data.hobbies)
              dbRepo.store(dbPayload).map { _ =>
                if(data.userType == "normal user")
              {Redirect(routes.Application.normalProfile())}
                else
                 { Redirect(routes.Application.adminProfile())}


              }

            } {
              _ => Future.successful(Ok("user already exists"))
            }
        }

      }
    )
  }

  def signInIndex: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(views.html.signIn()))
  }


  def signIn: Action[AnyContent] = Action.async { implicit request =>
    loginForm.loginForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(BadRequest(s"${formWithError.errors}"))
      },
      data => {
        dbRepo.get(data.username).map{
          optionalUser=>
            optionalUser.fold{
              NotFound("user does not exits")
            } { loginuser =>
              //if (loginuser.password == data.password && userForm.userForm.userType == "normal user") {*/
                Redirect(routes.Application.normalProfile())
              }


              }
            }
    )}



  def getUser(username: String) :Action[AnyContent] = Action.async { implicit request =>
    dbRepo.get(username).map{
      optionaluser=>
        optionaluser.fold{
          NotFound("user does not exists")
        }{
          user =>
            Ok(s"${user.firstname},${user.lastname},${user.age},${user.gender},${user.phoneNo}")
        }
    }
  }




  def resetPassword: Action[AnyContent] = Action.async { implicit request =>
    resetForm.resetForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(s"${formWithError.errors}"))
      },
      data => {
        dbRepo.get(data.username).map{
          optionalUser=>
            optionalUser.fold{
              NotFound("user does not exits")}{_=>

             Ok("password updated")


        }}
      }
        )
  }

  def normalProfile: Action[AnyContent] = Action.async{ implicit request =>
    Future.successful(Ok(views.html.normalUserProfile()))
  }
  def profile: Action[AnyContent] = Action.async{ implicit request =>
    userForm.userForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(views.html.profile(formWithError)))
      },
      data => {
        dbRepo.get(data.username).flatMap {
          optionalUser =>
            optionalUser.fold {
              val dbPayload: UserRepo= UserRepo(0,data.userType,data.firstname,data.middlename,data.lastname,data.username,
                data.password.password,data.phoneNo,data.gender,data.age,data.hobbies)
              dbRepo.store(dbPayload).map { _ =>
                Ok("updation successful")
              }
            } {
              _ => Future.successful(Ok(" already updated"))
            }
        }

      }
    )
  }

  def adminProfile: Action[AnyContent] = Action.async{ implicit request =>
    Future.successful(Ok(views.html.adminProfile()))
  }


  def viewAssignment: Action[AnyContent] = Action.async{ implicit request =>

        assignmentDbRepo.get.map{assgn =>
          Ok(views.html.viewAssignments(assgn))

            }

  }

  def addAssignmentIndex: Action[AnyContent] = Action.async{ implicit request =>

    Future.successful(Ok(views.html.addAssignments()))

  }

  def addAssignment: Action[AnyContent] = Action.async { implicit request =>
    assignmentForm.assignmentForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(s"${formWithError.errors}"))
      },
      data => {
        assignmentDbRepo.get(data.title).flatMap { optionalUser =>
          optionalUser.fold {
            val dbPayload: AssignmentRepo = AssignmentRepo(0, data.title, data.details)
            assignmentDbRepo.store(dbPayload).map { _ =>
              Redirect(routes.Application.profile())

            }
          } { _ =>
            Future.successful(Ok("User already exist"))
          }
        }
      }
    )
  }



  def viewUser: Action[AnyContent] = Action.async { implicit request =>
    dbRepo.get.map { data =>
      Ok(views.html.viewUser(data))
    }
  }

}
