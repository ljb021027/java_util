package com.MyUtils.kafka.json;

import com.MyUtils.kafka.WordCountProcessor;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class TestApplication {

    public static final String KAFKA_BROKER = "localhost:9092,localhost:9093,localhost:9094";
    public static final String INPUT_TOPIC = "input";
    public static final String OUTPUT_TOPIC = "output";


    public static void main(String[] args) throws InterruptedException {

        // Configure the Streams application.
        final Properties streamsConfiguration = getStreamsConfiguration(KAFKA_BROKER);

        Topology topology = createTopology(INPUT_TOPIC, OUTPUT_TOPIC);

        //创建和启动KStream
        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamsConfiguration);
        kafkaStreams.cleanUp();
        kafkaStreams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close the Streams application.
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }


    static Topology createTopology(String inputTopic, String outputTopic) {
        Duration duration = Duration.ofMinutes(1);

        Map<String, Map<Long, String>> limitMap = new HashMap<>();
        Map<Long, String> m = new HashMap<>();
        m.put(10L, "a 大于10");
        limitMap.put("a", m);


        String storeName = "Counts";
        StoreBuilder<KeyValueStore<String, Long>> countStoreSupplier =
                Stores.keyValueStoreBuilder(
                        Stores.inMemoryKeyValueStore(storeName),
                        Serdes.String(),
                        Serdes.Long());

//        final StoreBuilder<WindowStore<String, Long>> countStoreSupplier =
//                Stores.windowStoreBuilder(
//                        Stores.inMemoryWindowStore(storeName,
//                                duration,
//                                duration,
//                                true
//                        ),
//                        Serdes.String(),
//                        Serdes.Long());
//        WindowStore<String, Long> build = countStoreSupplier.build();


        JsonDeserializer<Ad> purchaseJsonDeserializer = new JsonDeserializer<>(Ad.class);
        JsonSerializer<Ad> purchaseJsonSerializer = new JsonSerializer<>();
        Serde<Ad> adSerde = Serdes.serdeFrom(purchaseJsonSerializer, purchaseJsonDeserializer);


        Topology topology = new Topology();
        topology.addSource("Source", Serdes.ByteArray().deserializer(), purchaseJsonDeserializer, inputTopic)
                .addProcessor("Process", () -> {
                    return new JsonProcessor(storeName, duration, limitMap);
                }, "Source")
                .addStateStore(countStoreSupplier, "Process")
                .addSink("Sink", outputTopic, new StringSerializer(), new LongSerializer(), "Process");


        return topology;
    }

    static Properties getStreamsConfiguration(final String bootstrapServers) {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-example");
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "test-example");
        streamsConfiguration.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MyTimestampExtractor.class);
        streamsConfiguration.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, "exactly_once");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Specify default (de)serializers for record keys and for record values.
//        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // Records should be flushed every 10 seconds. This is less than the default
        return streamsConfiguration;
    }

}
