package dev.rmiller.thwompchatbackend.message;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbc;
    private final RowMapper<Message> messageRowMapper;

    public MessageRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.messageRowMapper = (r, i) -> {
            Message rowObject = new Message();
            rowObject.setId(r.getInt("id"));
            rowObject.setText(r.getString("message_text"));
            rowObject.setSentAt(r.getTimestamp("sent_at"));
            rowObject.setSenderName(r.getString("username"));
            rowObject.setUserId(r.getLong("user_id"));
            rowObject.setChatId(r.getLong("chat_id"));
            return rowObject;
        };
    }

    public List<Message> findMessagesByChatId(long chatId) {
        String sql = """
                SELECT m.*, u.username FROM messages AS m
                JOIN chats AS c
                    ON m.chat_id = c.id
                JOIN users AS u
                    ON m.user_id = u.id
                WHERE chat_id = ?;""";

        return jdbc.query(sql, messageRowMapper, chatId);
    }

    public Message insertMessageFromUserIntoChat(String messageText, long userId, long chatId, String username) {
        String sql = """
                INSERT INTO messages (message_text, user_id, chat_id)
                VALUES (?, ?, ?)
                RETURNING *, 'setThis' as username;""";
        var message = jdbc.queryForObject(sql, messageRowMapper, messageText, userId, chatId);
        if (message != null) {
            message.setSenderName(username);
        }
        return message;
    }
}
