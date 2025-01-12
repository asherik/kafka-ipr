package obuch;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

@Slf4j
public class CreateTopic {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29099,localhost:39099,localhost:49099");

        try (AdminClient adminClient = AdminClient.create(props)) {
            NewTopic newTopic = new NewTopic("topik2", 1, (short) 3);

            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

            log.info("Топик создан");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
