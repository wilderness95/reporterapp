package hu.wilderness.reporterapp.dataacces.dao;

import hu.wilderness.reporterapp.dataacces.dao.parents.BaseJdbcDao;
import hu.wilderness.reporterapp.dataacces.mapper.UserMapper;
import hu.wilderness.reporterapp.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserJdbcDao extends BaseJdbcDao {
    /*https://www.baeldung.com/spring-jdbc-jdbctemplate*/
    @Override
    public String getTableName() {
        return "user";
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

    public User insert(User u) {
        Map<String, Object> parameters = new HashMap();
        parameters.put("name", u.getName());
        parameters.put("nick_name", u.getNickName());
        parameters.put("birth_date", u.getBirthDate());
        parameters.put("password", u.getPassword());
        parameters.put("email", u.getEmail());
        parameters.put("created_date", u.getCreatedDate());
        parameters.put("last_logged_in", u.getLastLoggedIn());
        parameters.put("active", u.getActive());


        Number result = insert.executeAndReturnKey(parameters);

        u.setId(result.intValue());
        return u;
    }

    public User update(User u) {
        if (u.getId() == null)
            return null;

        String sql = "update " + getTableName() + " set          " +
                "        name=?, " +
                "        nick_name=?, " +
                "        birth_date=?, " +
                "        password=?, " +
                "        email=?, " +
                "        created_date=?, " +
                "        last_logged_in=?, " +
                "        active=? " +
                "where " +
                "        id=? ";
        Object[] parameters = {
                u.getName(),
                u.getNickName(),
                u.getBirthDate(),
                u.getPassword(),
                u.getEmail(),
                u.getCreatedDate(),
                u.getLastLoggedIn(),
                u.getActive(),
                u.getId()
        };
        int result = jdbcTemplate.update(sql, parameters);
        return u;
    }
}
