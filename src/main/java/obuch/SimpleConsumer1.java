package obuch;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class SimpleConsumer1 {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("topik13"));

        consumer.poll(Duration.ofMillis(0));


        Set<TopicPartition> assignedPartitions = consumer.assignment();


        while (assignedPartitions.isEmpty()) {
            consumer.poll(Duration.ofMillis(100));
            assignedPartitions = consumer.assignment();
        }

        for (TopicPartition partition : assignedPartitions) {
            consumer.seek(partition, 2);
        }

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Принято сообщение: key = {}, value = {}, offset = {}", record.key(), record.value(), record.offset());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
