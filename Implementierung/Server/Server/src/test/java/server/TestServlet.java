package server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class TestServlet extends Mockito {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void isOnlineTest() {
		this.webClient.get().uri("/isonline/testplayer").exchange().expectStatus().isOk()
				.expectBody(String.class).isEqualTo("false");
	}
}
