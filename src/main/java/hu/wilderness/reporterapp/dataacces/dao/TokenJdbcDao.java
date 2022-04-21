package hu.wilderness.reporterapp.dataacces.dao;


import hu.wilderness.reporterapp.dataacces.dao.parents.BaseJdbcDao;
import hu.wilderness.reporterapp.dataacces.mapper.TokenMapper;
import hu.wilderness.reporterapp.domain.Token;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenJdbcDao extends BaseJdbcDao {


    @Override
    public String getTableName() {
        return "token";
    }

    public Token findAll() {
        //TODO fix it
        return new Token();
    }

    public int setActiveState(String token, boolean activeState) {
        String sql = "update " + getTableName() + " set active = ? where token = ?";
        Object[] params = {activeState, token};
        int result = jdbcTemplate.update(sql, params);
        return result;
    }

    public Token findByToken(String token) {
        String sql = "select * from " + getTableName() + " t where t.token = ?";
        Object[] params = {token};
        try {
            Token t = jdbcTemplate.queryForObject(sql, params, new TokenMapper());
            return t;
        } catch (DataAccessException e) {
            return null;
        }
    }


    public Token insert(Token t) {


        Map<String, Object> parameters = new HashMap();
        parameters.put("active", t.isActive());
        parameters.put("successful", t.isSuccessful());
        parameters.put("type", t.getType().toString());
        parameters.put("token", t.getToken());
        parameters.put("created_at", t.getCreatedAt());
        parameters.put("expires_at", t.getExpiresAt());
        parameters.put("confirmed_at", t.getConfirmedAt());

        Number result = insert.executeAndReturnKey(parameters);

        t.setId(result.longValue());

        return t;

    }

    public Token update(Token t) {
        if (t.getId() == null) return null;

        String sql =
                "update " + getTableName() + " " +
                        "set          " +
                        "        active=?, " +
                        "        successful=?, " +
                        "        type=?, " +
                        "        token=?, " +
                        "        created_at=?, " +
                        "        expires_at=?," +
                        "        confirmed_at=? " +
                        "where " +
                        "        id=? ";
        Object[] parameters = {
                t.isActive(),
                t.isSuccessful(),
                t.getType().toString(),
                t.getToken(),
                t.getCreatedAt(),
                t.getExpiresAt(),
                t.getConfirmedAt(),
                t.getId()
        };
        int result = jdbcTemplate.update(sql, parameters);
        return t;
    }
}
