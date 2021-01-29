package com.MyUtils.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.internals.InternalTopologyBuilder;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class Application {


    public static void main(String[] args) throws InterruptedException {
        String input = "abc";   //输入 topic
        String output = "recommender";  //输出 topic

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "logProcessor");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Configure the Streams application.
        final Properties streamsConfiguration = getStreamsConfiguration("localhost:9092");

        //使用Serdes类创建序列化/反序列化所需的Serde实例 Serdes类为以下类型提供默认的实现：String、Byte array、Long、Integer和Double。
        Serde<String> stringSerde = Serdes.String();


        StreamsBuilder builder = new StreamsBuilder();

        createWordCountStream(builder, input, output);

//        KStream<String, String> textLines = builder.stream(input, Consumed.with(stringSerde, stringSerde));

//        //源处理器
//        KStream<String, String> simpleFirstStream = builder.stream(input, Consumed.with(stringSerde, stringSerde));
//        //创建新的处理器节点 使用KStream.mapValues 将输入数据流以 abc: 拆分获取下标为 1 字符串
//        KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(line -> {
//            String[] split = line.split("abc:");
//            if (split.length > 1) {
//                return split[1];
//            } else {
//                return split[0];
//            }
//
//        });

//        KTable<String, Long> wordCounts = textLines
//                // Split each text line, by whitespace, into words.
//                .flatMapValues(value -> {
//                    System.out.println(value);
//                    List<String> ts = Arrays.asList(value.toLowerCase().split("\\W+"));
//                    System.out.println(ts);
//                    return ts;
//                })
//
//
//                // Group the text words as message keys
    //                .groupBy((key, value) -> {
//
//                    System.out.println(key);
//                    System.out.println(value);
//                    return value;
//                })
//
//                // Count the occurrences of each word (message key).
//                .count();
//
//
//        // 把转换结果输出到另一个topic
//        wordCounts.toStream().to(output, Produced.with(Serdes.String(), Serdes.Long()));

        //创建和启动KStream
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.cleanUp();
        kafkaStreams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close the Streams application.
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }


    static void createWordCountStream(final StreamsBuilder builder, String inputTopic, String outputTopic) {
        // Construct a `KStream` from the input topic "streams-plaintext-input", where message values
        // represent lines of text (for the sake of this example, we ignore whatever may be stored
        // in the message keys).  The default key and value serdes will be used.
        final KStream<String, String> textLines = builder.stream(inputTopic);

        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        final KTable<String, Long> wordCounts = textLines
                // Split each text line, by whitespace, into words.  The text lines are the record
                // values, i.e. we can ignore whatever data is in the record keys and thus invoke
                // `flatMapValues()` instead of the more generic `flatMap()`.
                .flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                // Group the split data by word so that we can subsequently count the occurrences per word.
                // This step re-keys (re-partitions) the input data, with the new record key being the words.
                // Note: No need to specify explicit serdes because the resulting key and value types
                // (String and String) match the application's default serdes.
                .groupBy((keyIgnored, word) -> {
                    System.out.println(word);
                    return word;
                })
                // Count the occurrences of each word (record key).
                .count();

        // Write the `KTable<String, Long>` to the output topic.
        wordCounts.toStream().to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));
    }

    static Properties getStreamsConfiguration(final String bootstrapServers) {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-lambda-example");
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "wordcount-lambda-example-client");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // Records should be flushed every 10 seconds. This is less than the default
        // in order to keep this example interactive.
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        // For illustrative purposes we disable record caches.
        streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        // Use a temporary directory for storing state, which will be automatically removed after the test.
        return streamsConfiguration;
    }

}
