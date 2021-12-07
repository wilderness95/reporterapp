package hu.wilderness.reporterapp.dataacces.mapper;

import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.utils.DateMapperHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper implements RowMapper<Report> {

    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        Report report = new Report();

        report.setId(rs.getInt("id"));
        report.setActive(rs.getBoolean("active"));
        report.setData(rs.getString("data"));
        report.setCounty(rs.getString("county"));
        report.setCity(rs.getString("city"));
        report.setAddress(rs.getString("address"));
        report.setName(rs.getString("name"));
        report.setEmail(rs.getString("email"));
        report.setCaseType(rs.getString("case_type"));
        report.setCreatedDate(DateMapperHelper.getDateTime(rs, "created_date"));
        report.setUser(new User(rs.getInt("user_id")));

        return report;
    }
}
