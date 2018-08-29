/*
package modals

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class PasswordRepo1(serialNo:Long,username:String,password:String)

trait PasswordTable extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  val userAssignmentQuery:TableQuery[UserAssignment] = TableQuery[UserAssignment]

  private[modals] class UserAssignment(tag:Tag) extends Table[AssignmentRepo](tag,"assignment") {

    def serialNo: Rep[Long] = column[Long]("serialNo", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title")

    def details: Rep[String] = column[String]("details")

    def * : ProvenShape[AssignmentRepo] = (serialNo, title, details) <> (AssignmentRepo.tupled, AssignmentRepo.unapply)
  }
}

class PasswordRepo @Inject()( protected val dbConfigProvider: DatabaseConfigProvider) extends Imple with AssignmentTable

trait Imple{
  self: AssignmentTable =>

  import profile.api._

  def store(assignmentRepo: AssignmentRepo):Future[Long] ={
    db.run{
      userAssignmentQuery returning userAssignmentQuery.map(_.serialNo) += assignmentRepo
    }
  }

  def get(title:String):Future[Option[AssignmentRepo]]={
    db.run{
      userAssignmentQuery.filter(_.title === title.toLowerCase).result.headOption
    }
  }

  def get: Future[Seq[AssignmentRepo]]={
    db.run{
      userAssignmentQuery.result
    }
  }
}
PasswordRepo {

}
*/
