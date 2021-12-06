package hu.wilderness.reporterapp.dataacces.mapper;

import hu.wilderness.reporterapp.domain.Parameter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterMapper implements RowMapper<Parameter> {

    @Override
    public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {

        Parameter parameter = new Parameter();

        parameter.setId(rs.getInt("id"));
        parameter.setKey(rs.getString("key"));
        parameter.setValue(rs.getString("value"));
        parameter.setDescription(rs.getString("description"));
        parameter.setActive(rs.getBoolean("active"));

        return parameter;
    }
}