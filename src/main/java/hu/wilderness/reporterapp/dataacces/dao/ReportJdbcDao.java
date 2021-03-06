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
public class ReportJdbcDao extends BaseJdbcDao {


    @Override
    public String getTableName() {
        return "report";
    }


    public Report findByToken(long tokenId) {
        String sql = "select * from " + getTableName() + " r where r.token_id = ?";
        Object[] params = {tokenId};
        try {
            Report r = jdbcTemplate.queryForObject(sql, params, new ReportMapper());
            return r;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public Report findById(long reportId) {
        String sql = "select * from " + getTableName() + " r where r.id = ?";
        Object[] params = {reportId};
        try {
            Report r = jdbcTemplate.queryForObject(sql, params, new ReportMapper());
            return r;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Report> findAll(){
        String sql = "select * from " + getTableName();
        List<Report> result = jdbcTemplate.query(sql, new ReportMapper());
        return  result;
    }

    public List<Report> findByActive(Boolean active) {
        String sql = "select * from "+getTableName() + " u where u.active = ?";
        Object[] params = {active};
        List<Report> result = jdbcTemplate.query (sql,params, new ReportMapper());
        return result;
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
        parameters.put("is_anonym", report.getIsAnonym());
        parameters.put("ip_address", report.getIpAddress());
        if(report.getToken() != null) parameters.put("token_id", report.getToken().getId()); // ilyen esetekn??l, ha objektumot sz??r??nk be mindig figyelni kell r??, hogy m??r az insertn??l levizsg??ljuk nem-e null, ??gy elker??lhet?? a NPE!!

        Number result = insert.executeAndReturnKey(parameters);

        report.setId(result.intValue());

        return report;

    }

    public Report update(Report report) {
        if (report.getId() == null) return null;

        String sql =
                "update "+getTableName()+" " +
                        "set          " +
                        "        active=?, " +
                        "        last_name=?, " +
                        "        first_name=?, " +
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
                        "        created_date=?," +
                        "        token_id=? " +
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
                report.getCreatedDate(),
                report.getToken()==null?null:report.getToken().getId(),
                report.getId()

        };
        int result = jdbcTemplate.update(sql, parameters);
        return report;
    }
}


