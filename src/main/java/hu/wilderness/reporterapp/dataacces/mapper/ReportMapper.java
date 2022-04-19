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
        report.setLastName(rs.getString("last_name"));
        report.setFirstName(rs.getString("first_name"));
        report.setCounty(rs.getString("county"));
        report.setAddress(rs.getString("address"));
        report.setEmail(rs.getString("email"));
        report.setCaseType(rs.getString("case_type"));
        report.setNotifiedDate(DateMapperHelper.getDateTime(rs, "notified_date"));
        report.setMessage(rs.getString("message"));
        report.setIsDanger(rs.getBoolean("is_danger"));
        report.setImg(rs.getString("img"));
        report.setCreatedDate(DateMapperHelper.getDateTime(rs, "created_date"));
        report.setIsDanger(rs.getBoolean("is_anonym"));
        report.setImg(rs.getString("ip_address"));

        return report;
    }
}
