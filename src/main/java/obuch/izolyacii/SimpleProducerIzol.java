package obuch.izolyacii;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Slf4j
public class SimpleProducerIzol {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        props.put("enable.idempotence", true);
        props.put("transactional.id", "test-transaction");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        producer.initTransactions();
        try {
            producer.beginTransaction();

            // Отправляем сообщение
            producer.send(new ProducerRecord<>("topik13", "kluch", "znachenie1"));
            log.info("Сообщение отправлено, но транзакция ещё не завершена");

//
//            producer.abortTransaction();
//            log.info("Транзакция откатилась");


//             producer.commitTransaction();
//            // log.info("Транзакция подтверждена");

        } catch (Exception e) {
            producer.abortTransaction();
            log.error("Ошибка в транзакции", e);
        } finally {
            producer.close();
        }
    }
}
