package modals

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class UserRepo(id:Long,userType:String,firstname:String,middlename:Option[String],lastname:String,username:String,
                    password:String,phoneNo:Int,gender:String,age:Int,hobbies:String)

trait UserTable extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  val userProfileQuery:TableQuery[UserProfile] = TableQuery[UserProfile]

  private[modals] class UserProfile(tag:Tag) extends Table[UserRepo](tag,"userprofile"){

    def id: Rep[Long] = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def userType: Rep[String] = column[String]("userType")
    def firstname: Rep[String] = column[String]("firstname")
    def middlename: Rep[Option[String]] = column[Option[String]]("middlename")
    def lastname: Rep[String] = column[String]("lastname")
    def username: Rep[String] = column[String]("username")
    def password: Rep[String] = column[String]("password")
    def age: Rep[Int] = column[Int]("age")
    def gender: Rep[String] = column[String]("gender")
    def hobbies: Rep[String] = column[String]("hobbies")
    def phoneNo: Rep[Int] = column[Int]("phoneNo")

    def * : ProvenShape[UserRepo] = (id,userType,firstname,middlename,lastname,username,password,phoneNo,gender,age,hobbies) <> (UserRepo.tupled , UserRepo.unapply)
  }
}

class DatabaseRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends Impl with UserTable

trait Impl{
  self: UserTable =>

  import profile.api._

  def store(userRepo: UserRepo):Future[Long] ={
    db.run{
      userProfileQuery returning userProfileQuery.map(_.id) += userRepo
    }
  }

  def get(username:String):Future[Option[UserRepo]]={
    db.run{
      userProfileQuery.filter(_.username === username.toLowerCase).result.headOption
    }

  }

  def get: Future[Seq[UserRepo]]={
    db.run{
      userProfileQuery.result
    }
  }
}
