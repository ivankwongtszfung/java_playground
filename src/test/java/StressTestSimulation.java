import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StressTestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost") // Base URL for the local server
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0");

    ScenarioBuilder scn = scenario("StressTestSimulation")
        .exec(http("request_1")
        .get("/api/singers")) // Adjust the path as needed
        .pause(1);

    {
        setUp(
            scn.injectOpen(
                nothingFor(4), // 1. Do nothing for 4 seconds
                atOnceUsers(10), // 2. Inject 10 users at once
                rampUsers(100).during(60), // 3. Inject 100 users over 60 seconds
                constantUsersPerSec(20).during(120) // 4. Inject 20 users per second for 120 seconds
            ).protocols(httpProtocol)
        );
    }
}
