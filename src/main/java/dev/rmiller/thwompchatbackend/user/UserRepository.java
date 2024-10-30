package dev.rmiller.thwompchatbackend.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {

    JdbcTemplate jdbc;

    UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    //TODO consider moving to security package
    public User findSecurityDetails(String username) {
        String sql = "SELECT id, username, password FROM users WHERE username = ?";
        RowMapper<User> rowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getLong("id"));
            rowObject.setUsername(r.getString("username"));
            rowObject.setPassword(r.getString("password"));
            return rowObject;
        };

        var queryList = jdbc.query(sql, rowMapper, username);

        if (queryList.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else if (queryList.size() == 1) {
            return queryList.get(0);
        } else {
            throw new IllegalStateException("Found more than one user with username: " + username);
        }
    }

}
