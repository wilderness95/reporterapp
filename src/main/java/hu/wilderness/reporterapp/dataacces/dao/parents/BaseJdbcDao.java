package hu.wilderness.reporterapp.dataacces.dao.parents;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public abstract class BaseJdbcDao implements InitializingBean, InterfaceJdbcDao {

    @Autowired
    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    protected SimpleJdbcInsert insert;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(getTableName()).usingGeneratedKeyColumns("id");

    }


}