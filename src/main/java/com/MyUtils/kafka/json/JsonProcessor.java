package com.MyUtils.kafka.json;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;
import java.util.Map;

public class JsonProcessor implements Processor<String, Ad> {

    private ProcessorContext context;
    private String storeName;
    private KeyValueStore<String, Long> kvStore;
    private Duration duration;
    private Map<String, Map<Long, String>> map;

    public JsonProcessor(String storeName, Duration duration, Map<String, Map<Long, String>> map) {
        this.storeName = storeName;
        this.duration = duration;
        this.map = map;

    }


    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        // keep the processor context locally because we need it in punctuate() and commit()
        this.context = context;
        // retrieve the key-value store named "Counts"
        kvStore = (KeyValueStore) context.getStateStore(this.storeName);
        // schedule a punctuate() method every second based on stream-time
        this.context.schedule(Duration.ofSeconds(10), PunctuationType.WALL_CLOCK_TIME, (timestamp) -> {
            System.out.println(kvStore.get("a"));
            //结算
//            System.out.println("结算" + timestamp);
//            KeyValueIterator<String, Long> iter = this.kvStore.all();
//            while (iter.hasNext()) {
//                KeyValue<String, Long> next = iter.next();
//                System.out.println("next" + next);
//                context.forward(next.key, next.value);
//            }
//            iter.close();
//            // commit the current processing progress
//            context.commit();
        });
    }

    @Override
    public void process(String s, Ad ad) {
        System.out.println(ad);
        Long aLong = kvStore.get(ad.getKey());
        if (aLong == null) {
            aLong = Long.valueOf(ad.getAuctionprice());
        } else {
            aLong += Long.valueOf(ad.getAuctionprice());
        }
        kvStore.put(ad.getKey(), aLong);
//        throw new NullPointerException();
//        if (aLong > 10) {
//            System.out.println("> 10," + aLong);
//        }
//        Windowed<String> windowedKey = new Windowed<String>(key, new TimeWindow(context.timestamp(), context.timestamp()));
//        kvStore.put(key, context.timestamp(), context.timestamp());
    }

//    @Override
//    public void punctuate(long timestamp) {
//        // this method is deprecated and should not be used anymore
//    }

    @Override
    public void close() {
        // close any resources managed by this processor
        // Note: Do not close any StateStores as these are managed by the library
    }

}
