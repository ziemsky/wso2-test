package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import flopmaster.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;

@RunWith(DataProviderRunner.class)
public class KafkaFeederTest {

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

        // given
        final Message message = new Message(stakeFactor, betStake, maxBetPercent, userName);
        sendMessage(toJson(message));

//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            sendMessage("message " + i);
//        });
    }

    private String toJson(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String message) {



        producer.send(new ProducerRecord<>("test", "MESSAGE", message));
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