package com.MyUtils.kafka.json;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class KafkaP {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        properties.put("acks", "all");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, TestApplication.KAFKA_BROKER);
//        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,  "172.30.29.248:32322,172.30.29.248:30971,172.30.29.248:31760");

//        properties.put("transactional.id", "first-transactional");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //消息实体
        ProducerRecord<String, String> record = null;
        long result = 0;
        for (int i = 0; i < 9; i++) {
            Thread.sleep(100);
            result += i;
            for (int j = 0; j < 6; j++) {
                String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
                Ad ad = Ad.builder().mediaid(j + "").auctionprice(10).date(format).callid(System.currentTimeMillis() / 1000).sid(i + "").build();

                String value = "";
                value = JSON.toJSONString(ad);
                record = new ProducerRecord<String, String>("input-0328", ad.getMediaid(), value);
                //发送消息
                producer.send(record);
            }
        }

        System.out.println("result" + result);
        producer.close();

    }
}
