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

        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setPassword(rs.getString("password"));
        user.setCounty(rs.getString("county"));
        user.setActive(rs.getBoolean("active"));
        user.setCreatedDate(DateHelper.getDateTime(rs, "created_date"));
        user.setLastLoggedIn(DateHelper.getDateTime(rs, "last_logged_in"));
        try {user.setRoleName(User.UserRole.valueOf(rs.getString("role_name")));} catch (Exception e) {}


        return user;
    }
}
