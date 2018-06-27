package basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;
import java.util.UUID;

@RunWith(DataProviderRunner.class)
public class BasicBetKafkaFeederTest {

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
    public void test(int amount, double factor, String meta) {

        final Message message = new Message(meta, new Bet(amount, factor));

        producer.send(new ProducerRecord<>("bets", "0", toJson(message)));
    }

    private String toJson(Object message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DataProvider
    public static Object[][] bets() {

        return new Object[][] {
            // amount   factor    meta
            {10,        1.1,      randomString()}, // should get logged by Siddhi app
            {5,         3,        randomString()}, // should get logged by Siddhi app
            {10,        0.9,      randomString()},
            {5,         0.8,      randomString()},
        };

    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}