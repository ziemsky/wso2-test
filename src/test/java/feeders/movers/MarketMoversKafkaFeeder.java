package feeders.movers;

import static feeders.movers.BetEvent.bet;
import static feeders.movers.PriceChangeEvent.price;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import java.util.stream.Stream;

public class MarketMoversKafkaFeeder {

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
    public void sendBetsAndPrices() {

        feed(
            bet("selection_A", 1, "user_a"),
            bet("selection_A", 10, "user_b"),

            price("selection_A", 1),
            price("selection_A", 10)
        );
    }

    private void feed(final Event... events) {
        Stream.of(events)
            .map(event -> new ProducerRecord<>(event.getTopicName(), "0", toJson(event)))
            .forEach(producer::send);
    }

    private String toJson(final Object pojo) {
        try {
            return objectMapper.writeValueAsString(pojo);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}