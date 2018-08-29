package forms


import play.api.data.Form
import play.api.data.Forms.{mapping,text}

case class UserAssignment(title:String,details:String)

class AssignmentForm {
  val assignmentForm = Form(
    mapping(
      "title"-> text,
      "details"-> text

    )(UserAssignment.apply)(UserAssignment.unapply)
  )

}
