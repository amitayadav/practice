package forms

import play.api.data.Form
import play.api.data.Forms.{mapping,text}


case class PasswordReset(username:String,password:String)

class ResetForm {

  val resetForm = Form(
      mapping(
        "username"-> text,
        "password"-> text

      )(PasswordReset.apply)(PasswordReset.unapply)
    )

  }
