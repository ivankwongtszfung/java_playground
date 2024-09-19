import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost") // Base URL for the local server
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0");

    ScenarioBuilder scn = scenario("BasicSimulation")
        .exec(http("request_1")
        .get("/api/singers")) // Adjust the path as needed
        .pause(1);

    {
        setUp(
            scn.injectOpen(
                atOnceUsers(10), // Inject 10 users at once
                rampUsers(100).during(60), // Inject 100 users over 60 seconds
                constantUsersPerSec(20).during(120) // Inject 20 users per second for 120 seconds
            ).protocols(httpProtocol)
        );
    }
}
