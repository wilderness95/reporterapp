package hu.wilderness.reporterapp.dataacces.mapper;



import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.utils.DateHelper;
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
        user.setBirthDate(DateHelper.getDateTime(rs, "birth_date"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedDate(DateHelper.getDateTime(rs, "created_date"));
        user.setLastLoggedIn(DateHelper.getDateTime(rs, "last_logged_in"));
        user.setActive(rs.getBoolean("active"));
        try {user.setRoleName(User.UserRole.valueOf(rs.getString("role_name")));} catch (Exception e) {}


        return user;
    }
}
