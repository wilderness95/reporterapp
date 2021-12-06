package hu.wilderness.reporterapp.dataacces.mapper;


import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.utils.DateMapperHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setNickName(rs.getString("nick_name"));
        user.setBirthDate(DateMapperHelper.getDateTime(rs, "birth_date"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedDate(DateMapperHelper.getDateTime(rs, "created_date"));
        user.setLastLoggedIn(DateMapperHelper.getDateTime(rs, "last_logged_in"));
        user.setActive(rs.getBoolean("active"));

        return user;
    }
}
