import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

class UserControllerSpec extends Specification {
  "Application" should {
    "return all users" in new WithApplication{
      val users = route(FakeRequest(GET, "/users")).get
      status(users) must equalTo(OK)
    }

    "create new user" in new WithApplication{
      var reqParams= "{\"name\": \"John\"}".getBytes()
      val users = route(FakeRequest(POST, "/create")
        .withHeaders(CONTENT_TYPE -> "application/json")
        .withJsonBody(Json.parse("""{ "name": "abc" }"""))
      ).get
      status(users) must equalTo(OK)
    }

    "find a user" in new WithApplication{
      val users = route(FakeRequest(GET, "/users/1")).get
      status(users) must equalTo(OK)
    }

    "delete a user" in new WithApplication{
      val users = route(FakeRequest(DELETE, "/users/1")).get
      status(users) must equalTo(NO_CONTENT)
    }
  }

}
