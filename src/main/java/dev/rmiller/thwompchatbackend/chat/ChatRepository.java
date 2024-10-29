package dev.rmiller.thwompchatbackend.chat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    JdbcTemplate jdbc;

    public ChatRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Chat> findChatsForUser(long userId) {
        String sql = """
                SELECT c.* FROM chats AS c
                JOIN user_chat_registry AS r
                    ON c.id = r.chat_id
                WHERE r.user_id = ?;""";

        RowMapper<Chat> rowMapper = (r, i) -> {
            Chat rowObject = new Chat();
            rowObject.setId(r.getInt("id"));
            rowObject.setName(r.getString("name"));
            rowObject.setCreatedAt(r.getTimestamp("created_at"));
            return rowObject;
        };

        return jdbc.query(sql, rowMapper, userId);
    }

    public boolean hasUser(long chatId, long userId) {
        String sql = """
                SELECT COUNT(*) = 1 FROM user_chat_registry
                WHERE chat_id = ? AND  user_id = ?;""";

        return Boolean.TRUE.equals(jdbc.queryForObject(sql, Boolean.class, chatId, userId));
    }

}
