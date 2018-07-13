package feeders.movers;

import static feeders.movers.Topic.ALERTS;
import static feeders.movers.json.incoming.Alert.alert;
import static feeders.movers.json.outgoing.BetEvent.bet;
import static feeders.movers.json.outgoing.PriceChangeEvent.price;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feeders.movers.json.incoming.Alert;
import feeders.movers.json.incoming.AlertEvent;
import feeders.movers.json.outgoing.Event;
import feeders.movers.json.outgoing.Message;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class MarketMoversTest {

    private static final String KAFKA_ADDRESS = "localhost:9092";
    private ObjectMapper objectMapper = new ObjectMapper();
    private Producer<String, String> producer;
    private Consumer<String, String> consumer;

    @Before
    public void setUp() {

        final Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        producerProps
            .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps
            .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(producerProps);

        final Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        consumerProps
            .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "market-movers_test");
        consumer = new KafkaConsumer<>(consumerProps);

        consumer.subscribe(singletonList(ALERTS.getTopicName()));
    }

    @After
    public void tearDown() {
        producer.close();
        consumer.close();
    }

    @Test
    public void sendBetsAndPrices() {

        // given
        feedsOf(
            bet("selection_A", "user_a"),
            bet("selection_B", "user_b"),
            bet("selection_C", "user_c"),

            price("selection_A", 1),
            price("selection_A", 2),
            price("selection_A", 10),
            price("selection_A", 3),
            price("selection_A", 20),
            price("selection_B", 22),
            price("selection_A", 26),
            price("selection_B", 7)
        );

        final Alert[] expectedAlerts = {
            alert("selection_A", "user_a"),
            alert("selection_A", "user_a"),
        };

        // when
        final Collection<Alert> actualAlerts = read(expectedAlerts.length, 1);

        // then
        assertThat(actualAlerts).containsExactly(expectedAlerts);
    }

    private Collection<Alert> read(int maxEventsCount, int timeoutInSecs) {

        final List<Alert> alerts = new ArrayList<>();

        final StopWatch stopWatch = new StopWatch();

        do {
            stopWatch.start();
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(500);

            consumerRecords.forEach(record -> alerts.add(fromJson(record.value()).getEvent()));

            stopWatch.stop();
        } while (
            // + 1 above ensures we capture any extraneous events should they be sent
            alerts.size() < maxEventsCount + 1
                && stopWatch.getTotalTimeSeconds() < timeoutInSecs
            );

        return alerts;
    }

    private void feedsOf(final Event... events) {
        Stream.of(events)
            .map(Message::new)
            .map(event -> new ProducerRecord<>(event.getTopic().getTopicName(), "0", toJson(event)))
            .forEach(producer::send);
    }

    private String toJson(final Object pojo) {
        try {
            return objectMapper.writeValueAsString(pojo);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private AlertEvent fromJson(final String payload) {
        try {
            return objectMapper.readValue(payload, AlertEvent.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}