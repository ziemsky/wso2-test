package flopmaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;

@RunWith(DataProviderRunner.class)
public class FlopmasterKafkaFeederTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Producer<String, String> producer;

    @Before
    public void setUp() {

        final Properties properties = new Properties();

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(properties);
    }

    @After
    public void tearDown() {
        producer.close();
    }

    @Test
    @UseDataProvider("bets")
    public void sendBets(double stakeFactor, double betStake, double maxBetPercent, String userName) {

        final Bet bet = new Bet(stakeFactor, betStake, maxBetPercent, userName);

        producer.send(new ProducerRecord<>("bets", "0", toJson(bet)));
    }

    @DataProvider
    public static Object[][] bets() {

        return new Object[][] {
            // stakeFactor, betStake, maxBetPercent, userName
            {0.3,           5.0,      0,             "user_a"},
            {0.3,           0.0,      75,            "user_b"},
            {0.5,           0.0,      0,             "user_c"},
            {0.0,           0.0,      0,             "FLOPMASTER"},
            {0.0,           0.0,      25,            "FLOPMASTER"},
        };
    }

    private String toJson(Bet bet) {
        try {
            return objectMapper.writeValueAsString(bet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}