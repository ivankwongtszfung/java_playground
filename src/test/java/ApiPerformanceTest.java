import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ApiPerformanceTest extends Simulation {

    // Define the base URL of the API
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost") // Base URL for the local server
        .acceptHeader("application/json");

    // Define the scenario
    ScenarioBuilder scn = scenario("API Load Test")
        .exec(http("Get Request")
            .get("/api/singers") // Adjust the path as needed
            .check(status().is(200))); // Check if the response status is 200 OK


    {
        // Define the load profile
        setUp(scn.injectOpen(
            // Define a ramp-up load profile
            rampUsersPerSec(1).to(10000).during(600) // Adjust the users per second and duration
        ).protocols(httpProtocol))
        .assertions(
            global().successfulRequests().percent().is(100.0) // Ensure all requests are successful
        );
    }
}
