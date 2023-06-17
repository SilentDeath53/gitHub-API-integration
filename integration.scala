import dispatch._
import dispatch.Defaults._
import scala.concurrent.Future
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

object GitHubAPIExample extends App {

  val baseUrl = "https://api.github.com"
  val accessToken = "YOUR_PERSONAL_ACCESS_TOKEN"

  def getUser(username: String): Future[Unit] = {
    val request = dispatch.url(s"$baseUrl/users/$username").addHeader("Authorization", s"token $accessToken")
    Http(request OK as.String).map { response =>
      println(response)
    }
  }

  def getRepositories(username: String): Future[Unit] = {
    val request = dispatch.url(s"$baseUrl/users/$username/repos").addHeader("Authorization", s"token $accessToken")
    Http(request OK as.String).map { response =>
      println(response)
    }
  }

  // Example usage
  val username = "your_github_username"
  val userResult = getUser(username)
  userResult.onComplete {
    case Success(_) => getRepositories(username)
    case Failure(ex) => println(s"Failed to retrieve user information: ${ex.getMessage}")
  }
}
