package obuch;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class CreateTopic {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");

        try (AdminClient adminClient = AdminClient.create(props)) {
            // Конфигурации для топика
            Map<String, String> topicConfig = new HashMap<>();
            topicConfig.put("min.insync.replicas", "3");

            // Создание топика с конфигурациями
            NewTopic newTopic = new NewTopic("topik13", 1, (short) 3)
                    .configs(topicConfig);

            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

            log.info("Топик создан");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
