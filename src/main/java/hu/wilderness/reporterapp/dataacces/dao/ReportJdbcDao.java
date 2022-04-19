package hu.wilderness.reporterapp.dataacces.dao;

import hu.wilderness.reporterapp.dataacces.dao.parents.BaseJdbcDao;
import hu.wilderness.reporterapp.domain.Report;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportJdbcDao extends BaseJdbcDao {


    @Override
    public String getTableName() {
        return "report";
    }

    public Report insert(Report report) {


        Map<String, Object> parameters = new HashMap();
        parameters.put("active", report.isActive());
        parameters.put("last_name", report.getLastName());
        parameters.put("first_name", report.getFirstName());
        parameters.put("county", report.getCounty());
        parameters.put("address", report.getAddress());
        parameters.put("email", report.getEmail());
        parameters.put("case_type", report.getCaseType());
        parameters.put("notified_date", report.getNotifiedDate());
        parameters.put("message", report.getMessage());
        parameters.put("is_danger", report.getIsDanger());
        parameters.put("img", report.getImg());
        parameters.put("created_date", report.getCreatedDate());
        parameters.put("is_anonym", report.getIsDanger());
        parameters.put("ip_address", report.getImg());

        Number result = insert.executeAndReturnKey(parameters);

        report.setId(result.intValue());

        return report;

    }

    public Report update(Report report) {
        if (report.getId() == null) return null;

        String sql =
                "update " + getTableName() + " " +
                        "set          " +
                        "        active=?, " +
                        "        lastName=?, " +
                        "        firstName=?, " +
                        "        county=?, " +
                        "        address=?, " +
                        "        email=?, " +
                        "        case_type=?," +
                        "        notified_date=?," +
                        "        message=?," +
                        "        is_danger=?," +
                        "        img=?," +
                        "        is_anonym=?," +
                        "        ip_address=?," +
                        "        created_date=?" +
                        "where " +
                        "        id=? ";
        Object[] parameters = {
                report.isActive(),
                report.getLastName(),
                report.getFirstName(),
                report.getCounty(),
                report.getAddress(),
                report.getEmail(),
                report.getCaseType(),
                report.getNotifiedDate(),
                report.getMessage(),
                report.getIsDanger(),
                report.getImg(),
                report.getIsAnonym(),
                report.getIpAddress(),
                report.getCreatedDate()

        };
        int result = jdbcTemplate.update(sql, parameters);
        return report;
    }
}


