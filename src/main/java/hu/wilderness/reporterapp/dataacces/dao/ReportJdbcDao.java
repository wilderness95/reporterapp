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
        return "token";
    }

    public Report insert(Report report) {


        Map<String, Object> parameters = new HashMap();
        parameters.put("active", report.isActive());
        parameters.put("data", report.getData());
        parameters.put("county", report.getCounty());
        parameters.put("city", report.getCity());
        parameters.put("address", report.getAddress());
        parameters.put("name", report.getName());
        parameters.put("email", report.getEmail());
        parameters.put("case_type", report.getCaseType());
        parameters.put("created_date", report.getCreatedDate());
        parameters.put("user_id", report.getUser().getId());

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
                        "        data=?, " +
                        "        county=?, " +
                        "        city=?, " +
                        "        address=?," +
                        "        name=?," +
                        "        email=?," +
                        "        case_type=?," +
                        "        created_date=?," +
                        "        user_id=?" +
                        "where " +
                        "        id=? ";
        Object[] parameters = {
                report.isActive(),
                report.getData(),
                report.getCounty(),
                report.getCity(),
                report.getAddress(),
                report.getName(),
                report.getEmail(),
                report.getCaseType(),
                report.getCreatedDate(),
                report.getUser().getId()

        };
        int result = jdbcTemplate.update(sql, parameters);
        return report;
    }
}


