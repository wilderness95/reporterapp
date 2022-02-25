package hu.wilderness.reporterapp.dataacces.dao;

import hu.wilderness.reporterapp.dataacces.dao.parents.BaseJdbcDao;
import hu.wilderness.reporterapp.dataacces.mapper.RoleMapper;
import hu.wilderness.reporterapp.domain.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RoleJdbcDao extends BaseJdbcDao {

    @Override
    public String getTableName() {
        return "role";
    }

    public Role findByRole(String role) {
        String sql = "select * from" + getTableName() + " r where r.role = ?";
        Object[] params = {role};
        try {
            Role r = jdbcTemplate.queryForObject(sql, params, new RoleMapper());
            return r;
        } catch (DataAccessException e) {
            return null;
        }
    }
}
