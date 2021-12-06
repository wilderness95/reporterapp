package hu.wilderness.reporterapp.service;

import hu.wilderness.reporterapp.dataacces.dao.ParameterJdbcDao;
import hu.wilderness.reporterapp.domain.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    ParameterJdbcDao parameterJdbcDao;

    public List<Parameter> getAllParameter() {
        List<Parameter> paramList = parameterJdbcDao.findAll();
        return paramList;
    }

    public void listParam(){
        List<Parameter> parameterList = getAllParameter();
        for (Parameter param : parameterList ) {
            log.debug(param.toString());
        }
    }

    private Parameter getParameter(String name) {
        Parameter parameter = parameterJdbcDao.findByKeyAndActive(name);
        return parameter;
    }

    public String getStringParameter(String name, String defaultValue) {
        String parameter = getStringParameter(name);
        if (parameter == null) {
            log.debug("missing parameter " + name + " from database, setting to default value: " + defaultValue);
            return defaultValue;
        }
        return parameter;
    }

    public String getStringParameter(String name) {
        Parameter parameter = getParameter(name);
        if (parameter == null)
            return null;
        return parameter.getValue();
    }

    public Integer getIntegerParameter(String name, Integer defaultValue) {
        Integer intPara = getIntegerParameter(name);
        if (intPara == null) {
            log.debug("missing parameter: " + name + " from database, setting to default value: " + defaultValue);
            return defaultValue;
        }
        return intPara;
    }

    public Integer getIntegerParameter(String name) {
        Parameter parameter = getParameter(name);
        if (parameter == null)
            return null;

        Integer intPara = null;
        try {
            intPara = Integer.parseInt(parameter.getValue());
        } catch (Exception e) {
        }
        return intPara;
    }

    public Boolean getBooleanParameter(String name, Boolean defaultValue) {
        Boolean boolPara = getBooleanParameter(name);
        if (boolPara == null) {
            log.debug("missing parameter: " + name + " from database, setting to default value: " + defaultValue);
            return defaultValue;
        }
        return boolPara;
    }

    public Boolean getBooleanParameter(String name) {
        Parameter parameter = getParameter(name);
        if (parameter == null)
            return null;
        Boolean boolPara = null;
        try {
            boolPara = Boolean.parseBoolean(parameter.getValue());
        } catch (Exception e) {
        }
        return boolPara;
    }
}