package com.MyUtils.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

public class WordCountProcessor implements Processor<String, String> {

    private ProcessorContext context;
    private KeyValueStore<String, Long> kvStore;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        // keep the processor context locally because we need it in punctuate() and commit()
        this.context = context;

        // retrieve the key-value store named "Counts"
        kvStore = (KeyValueStore) context.getStateStore("Counts");

        // schedule a punctuate() method every second based on stream-time
        this.context.schedule(Duration.ofSeconds(1000), PunctuationType.STREAM_TIME, (timestamp) -> {
            KeyValueIterator<String, Long> iter = this.kvStore.all();
            while (iter.hasNext()) {
                KeyValue<String, Long> entry = iter.next();
                context.forward(entry.key, entry.value.toString());
            }
            iter.close();

            // commit the current processing progress
            context.commit();
        });
    }

    @Override
    public void process(String s, String s2) {

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
