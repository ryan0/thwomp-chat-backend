package dev.rmiller.thwompchatbackend.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestRowGenerator {

    private final PasswordEncoder encoder;
    private final JdbcTemplate jdbc;

    @Value("${thwomp.gen-test-rows}")
    private boolean genTestUsers;

    TestRowGenerator(PasswordEncoder encoder, JdbcTemplate jdbc) {
        this.encoder = encoder;
        this.jdbc = jdbc;
    }

    @PostConstruct
    public void genUsers() {
        if (genTestUsers) {
            genTestUser(1);
            genTestUser(2);
            genTestUser(3);

            genTestChat(1);
            genTestChat(2);

            genTestChatRegistry(1, 1);
            genTestChatRegistry(2, 1);

            genTestMessages("testing", 1, 1);
            genTestMessages("hello", 2, 1);
        }
    }

    private void genTestUser(int i) {
        String userSQL = """
                    INSERT INTO users (username, password, email)
                    VALUES (?, ?, ?);""";
        jdbc.update(
                userSQL,
                "test_user" +  i,
                encoder.encode("TestPass123!"),
                "test" + i + "@gmail.com");
    }

    private void genTestChat(int i) {
        String userSQL = """  
                INSERT INTO chats (name)
                VALUES (?);""";
        jdbc.update(userSQL, "Test Chat " +  i);
    }

    private void genTestChatRegistry(int userId, int chatId) {
        String userSQL = """  
                INSERT INTO user_chat_registry (user_id, chat_id)
                VALUES (?, ?)""";
        jdbc.update(userSQL, userId, chatId);
    }

    private void genTestMessages(String text, int userId, int chatId) {
        String userSQL = """  
                INSERT INTO messages (message_text, user_id, chat_id)
                VALUES (?, ?, ?)""";
        jdbc.update(userSQL, text, userId, chatId);
    }

}
