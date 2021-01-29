//package com.MyUtils.kafka;
//
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.Consumed;
//import org.apache.kafka.streams.kstream.Produced;
//
//public class ZMartKafkaStreamsApp {
//
//    public static void main(String[] args) {
//        // some details left out for clarity
//
//        StreamsConfig streamsConfig = new StreamsConfig(getProperties());
//        “JsonSerializer<Purchase> purchaseJsonSerializer = new
//                JsonSerializer<>();
//        JsonDeserializer<Purchase> purchaseJsonDeserializer =
//  new JsonDeserializer<>(Purchase.class); 　　←---　创建Serde，数据格式是JSON
//        Serde<Purchase> purchaseSerde =
//  Serdes.serdeFrom(purchaseJsonSerializer, purchaseJsonDeserializer);
//        //Other Serdes left out for clarity
//
//        Serde<String> stringSerde = Serdes.String();
//
//        StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        KStream<String,Purchase> purchaseKStream =
//  streamsBuilder.stream("transactions",
//                  Consumed.with(stringSerde, purchaseSerde))
//  .mapValues(p -> Purchase.builder(p).maskCreditCard().build());　　←---　创建源和第一个处理器
//        KStream<String, PurchasePattern> patternKStream =
//  purchaseKStream.mapValues(purchase ->
//                  PurchasePattern.builder(purchase).build());　　←---　创建PurchasePattern
//                处理器
//
//        patternKStream.to("patterns",
//                  Produced.with(stringSerde,purchasePatternSerde));
//
//        KStream<String, RewardAccumulator> rewardsKStream =
//  purchaseKStream.mapValues(purchase ->
//                  RewardAccumulator.builder(purchase).build());　　←---　创建RewardAccumulator处理器
//
//        rewardsKStream.to("rewards",
//                  Produced.with(stringSerde,rewardAccumulatorSerde));
//
//        purchaseKStream.to("purchases",
//                  Produced.with(stringSerde,purchaseSerde)); 　　←---　创建存储接收器，一个被存储消费者使用的主题
//
//        KafkaStreams kafkaStreams =
//  new KafkaStreams(streamsBuilder.build(),streamsConfig);
//        kafkaStreams.start();
//    }
//}