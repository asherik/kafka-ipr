package obuch;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Callback;

import java.util.Properties;

@Slf4j
public class SimpleProducer {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "1");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            ProducerRecord<String, String> record = new ProducerRecord<>("topik13", "kluch", "znachenie");

            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        log.error("Ошибка при отправке сообщения", e);
                    } else {
                        log.info("Отправлено в топик {} с офсетом {}", metadata.topic(), metadata.offset());
                    }
                }
            });

        } catch (Exception e) {
            log.error("Произошла ошибка в процессе отправки", e);
        } finally {
            producer.close();
        }
    }
}
