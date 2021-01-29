package com.MyUtils.kafka.json;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MyTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        Ad ad = (Ad) record.value();
        return Long.valueOf(ad.getCallid());
    }
}
