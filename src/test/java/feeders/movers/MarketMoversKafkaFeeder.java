package feeders.movers;

import static feeders.movers.Alert.alert;
import static feeders.movers.BetEvent.bet;
import static feeders.movers.PriceChangeEvent.price;
import static feeders.movers.Topic.ALERTS;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class MarketMoversKafkaFeeder {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Producer<String, String> producer;

    private Consumer<String, String> consumer;
    private static final String KAFKA_ADDRESS = "localhost:9092";

    @Before
    public void setUp() {

        final Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(producerProps);

        final Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "0");
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
            bet("selection_A", "user_b"),

            price("selection_A", 1),
            price("selection_A", 10)
        );

        // when
        final Collection<Alert> actualAlerts = read(10);

        // then
        assertThat(actualAlerts).containsExactly(
            alert("selection_A", "user_a")
        );
    }

    private Collection<Alert> read(int maxEventsCount) {

        final List<Alert> alerts = new ArrayList<>();

        final StopWatch stopWatch = new StopWatch();

        do {
            stopWatch.start();
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);

            consumerRecords.forEach(record -> alerts.add(fromJson(record.value())));
            stopWatch.stop();
        } while (alerts.size() < maxEventsCount && stopWatch.getTotalTimeSeconds() < 5);

        return alerts;
    }

    private void feedsOf(final Event... events) {
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

    private Alert fromJson(final String payload) {
        try {
            return objectMapper.readValue(payload, Alert.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}