package forms

import play.api.data.Form
import play.api.data.Forms.{mapping,text}

case class LoginUser(username:String,password:String)

class LoginForm {
  val loginForm = Form(
    mapping(
      "username"-> text,
      "password"-> text

    )(LoginUser.apply)(LoginUser.unapply)
  )

}
