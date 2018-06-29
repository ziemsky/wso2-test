package flopmaster;

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

@RunWith(DataProviderRunner.class)
public class FlopmasterHttpRuleTest {

    private static final String ALERTS_URL = "http://localhost:5005/alerts";
    final RestTemplate restTemplate = new RestTemplate();

    @Test
    @UseDataProvider("bets")
    public void test(double stakeFactor, double betStake, double maxBetPercent, String userName) {

        // given
        final Bet bet = new Bet(stakeFactor, betStake, maxBetPercent, userName);

        final RequestEntity<Bet> requestEntity = RequestEntity
            .post(URI.create(ALERTS_URL))
            .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
            .body(bet);

        // when
        restTemplate.exchange(requestEntity, String.class);

        // then
        // see logs
    }

    @DataProvider
    public static Object[][] bets() {

        return new Object[][] {
            {0.3, 5.0, 0,  "user_a"},
            {0.3, 0.0, 75, "user_b"},
            {0.5, 0.0, 0,  "user_c"},
            {0.0, 0.0, 0,  "FLOPMASTER"},
            {0.0, 0.0, 25,  "FLOPMASTER"},
        };
    }
}


