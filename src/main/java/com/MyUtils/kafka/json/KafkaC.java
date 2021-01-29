package com.MyUtils.kafka.json;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaC {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        localHost.getAddress();
        localHost.getHostAddress();
//        Properties properties = new Properties();
//        properties.put("key.deserializer", StringDeserializer.class.getName());
//        properties.put("value.deserializer", LongDeserializer.class.getName());
//        properties.put("group.id", "test");
//        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,  "172.30.29.248:31092");
//        KafkaConsumer consumer = new KafkaConsumer<String, String>(properties);
//        consumer.subscribe(Arrays.asList("input-0318"));
//        System.out.println("start");
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(record);
//            }
//        }
    }
}
