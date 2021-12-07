package hu.wilderness.reporterapp.service;

import hu.wilderness.reporterapp.dataacces.dao.ReportJdbcDao;
import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.dto.ReportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ReportJdbcDao reportJdbcDao;

    public Report createNew(ReportDto reportDto) {
        Report report = new Report();

        report.setActive(true);
        report.setData(reportDto.getData());
        report.setCounty(reportDto.getCounty());
        report.setCity(reportDto.getCity());
        report.setAddress(reportDto.getAddress());
        report.setName(reportDto.getName());
        report.setEmail(reportDto.getEmail());
        report.setCaseType(report.getCaseType());
        report.setCreatedDate(new Date());

        report = save(report);
        log.debug("create a new report: " + report.toString());
        return report;
    }

    public Report save(Report report) {
        if (report.getId() == null) {
            report = reportJdbcDao.insert(report);
            log.debug("report has been succesfully saved");
        } else {
            report = reportJdbcDao.update(report);
            log.debug("report has been succesfully updated");
        }
        return report;
    }

}
