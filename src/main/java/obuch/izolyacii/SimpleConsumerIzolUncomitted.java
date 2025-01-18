package obuch.izolyacii;

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
public class SimpleConsumerIzolUncomitted {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        // Уровень изоляции
        props.put("isolation.level", "read_uncommitted");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("topik13"));

        try {

            consumer.poll(Duration.ofMillis(0));
            Set<TopicPartition> assignedPartitions = consumer.assignment();

            while (assignedPartitions.isEmpty()) {
                consumer.poll(Duration.ofMillis(100));
                assignedPartitions = consumer.assignment();
            }


            TopicPartition partition = assignedPartitions.iterator().next();


            while (true) {

                long startPosition = consumer.position(partition);
                log.info("Начальная позиция: {}", startPosition);

                consumer.seek(partition, startPosition - 3);


                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Принято сообщение: key = {}, value = {}, offset = {}",
                            record.key(), record.value(), record.offset());
                }


                long currentPosition = consumer.position(partition);
                log.info("Текущая позиция: {}", currentPosition);
            }
        } finally {
            consumer.close();
        }
    }
}
