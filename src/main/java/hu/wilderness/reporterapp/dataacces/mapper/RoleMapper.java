package hu.wilderness.reporterapp.dataacces.mapper;

import hu.wilderness.reporterapp.domain.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();

        role.setId(rs.getInt("id"));
        role.setRole(rs.getString("role"));

        return role;
    }
}