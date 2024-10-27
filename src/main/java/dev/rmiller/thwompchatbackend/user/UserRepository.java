package dev.rmiller.thwompchatbackend.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    JdbcTemplate jdbc;

    UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public User findSecurityDetails(String username) {
        String sql = "SELECT id, username, password FROM users WHERE username = ?";
        RowMapper<User> rowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getLong("id"));
            rowObject.setUsername(r.getString("username"));
            rowObject.setPassword(r.getString("password"));
            return rowObject;
        };

        return jdbc.queryForObject(sql, rowMapper, username);
    }

}
