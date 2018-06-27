package basic;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@RunWith(DataProviderRunner.class)
public class BasicRuleTest {

    private static final String ALERTS_URL = "http://localhost:5005/alerts";
    final RestTemplate restTemplate = new RestTemplate();

    @Test
    @UseDataProvider("bets")
    public void test(int amount, double factor, String meta) {

        // given
        final Message message = new Message(meta, new Bet(amount, factor));

        final RequestEntity<Message> requestEntity = RequestEntity
            .post(URI.create(ALERTS_URL))
            .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
            .body(message);

        // when
        restTemplate.exchange(requestEntity, String.class);

        // then
        // see logs
    }


    @DataProvider
    public static Object[][] bets() {

        return new Object[][] {
            // amount   factor    meta
            {10,        1.1,      randomString()},
            {5,         3,        randomString()},
            {10,        0.9,      randomString()},
            {5,         0.8,      randomString()},
        };

    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}


