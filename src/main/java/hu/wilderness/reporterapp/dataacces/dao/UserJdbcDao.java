package hu.wilderness.reporterapp.dataacces.dao;

import hu.wilderness.reporterapp.dataacces.dao.parents.BaseJdbcDao;
import hu.wilderness.reporterapp.dataacces.mapper.ReportMapper;
import hu.wilderness.reporterapp.dataacces.mapper.UserMapper;
import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserJdbcDao extends BaseJdbcDao {
    /*https://www.baeldung.com/spring-jdbc-jdbctemplate*/
    @Override
    public String getTableName() {
        return "user";
    }

    public User findById(long userId) {
        String sql = "select * from " + getTableName() + " u where u.id = ?";
        Object[] params = {userId};
        try {
            User u = jdbcTemplate.queryForObject(sql, params, new UserMapper());
            return u;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public User findByEmailAddress(String emailAddress){
        String sql = "select * from " + getTableName() + " u where u.email = ? ";
        Object[] params = {emailAddress};
        try {
            User user = jdbcTemplate.queryForObject(sql, params, new UserMapper());
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<User> findAll() {
        String sql = "select * from "+getTableName();
        List<User> result = jdbcTemplate.query(sql, new UserMapper());
        return result;
    }

    public User insert(User u) {
        Map<String, Object> parameters = new HashMap();
        parameters.put("first_name", u.getFirstName());
        parameters.put("last_name", u.getLastName());
        parameters.put("email", u.getEmail());
        parameters.put("phone_number", u.getPhoneNumber());
        parameters.put("password", u.getPassword());
        parameters.put("county",u.getCounty());
        parameters.put("active", u.getActive());
        parameters.put("created_date", u.getCreatedDate());
        parameters.put("last_logged_in", u.getLastLoggedIn());
        parameters.put("role_name", u.getRoleName());




        Number result = insert.executeAndReturnKey(parameters);

        u.setId(result.longValue());
        return u;
    }

    public User update(User u) {
        if (u.getId() == null)
            return null;

        String sql = "update " + getTableName() + " set          " +
                "        first_name=?, " +
                "        last_name=?, " +
                "        email=?, " +
                "        phone_number=?, " +
                "        password=?, " +
                "        county=?, " +
                "        active=?, " +
                "        created_date=?, " +
                "        last_logged_in=?, " +
                "        role_name=?   " +
                "where " +
                "        id=? ";
        Object[] parameters = {
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getPassword(),
                u.getCounty(),
                u.getActive(),
                u.getCreatedDate(),
                u.getLastLoggedIn(),
                u.getRoleName(),
                u.getId()
        };
        int result = jdbcTemplate.update(sql, parameters);
        return u;
    }
}
