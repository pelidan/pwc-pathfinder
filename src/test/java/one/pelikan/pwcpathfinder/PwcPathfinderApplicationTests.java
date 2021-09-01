package one.pelikan.pwcpathfinder;

import one.pelikan.pwcpathfinder.dto.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PwcPathfinderApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSuccess() {
        Route result = restTemplate.getForObject(getUrl("CZE", "ITA"), Route.class);
        assertThat(result).isNotNull();
        assertThat(result.getRoute()).isEqualTo(List.of("CZE", "AUT", "ITA"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CZ", "C8Z", "CZCZ", "CZ+"})
    void testBadFormatOrigin(String origin) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(origin, "ITA"), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CZ", "C8Z", "CZCZ", "CZ+"})
    void testBadFormatDestination(String destination) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("ITA", destination), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testNonexistentCountryOrigin() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("XXX", "ITA"), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testNonexistentCountryDestination() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("CZE", "YYY"), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testRouteNotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("CZE", "USA"), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private String getUrl(String origin, String destination) {
        return String.format("http://localhost:%d/routing/%s/%s", port, origin, destination);
    }
}
