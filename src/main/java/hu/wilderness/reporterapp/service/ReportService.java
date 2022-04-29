package hu.wilderness.reporterapp.service;

import hu.wilderness.reporterapp.dataacces.dao.ReportJdbcDao;
import hu.wilderness.reporterapp.dataacces.dao.TokenJdbcDao;
import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.dto.ReportDto;
import hu.wilderness.reporterapp.exception.ReporterException;
import hu.wilderness.reporterapp.utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    @Autowired
    ReportJdbcDao reportJdbcDao;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenJdbcDao tokenJdbcDao;

    public Report createNew(ReportDto reportDto, HttpServletRequest request, MultipartFile multipartFile) {
        String fileName = getFileName(multipartFile);
        Report report = new Report();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(reportDto, report);

        report.setActive(reportDto.getIsAnonym());
        report.setLastName(reportDto.getLastName());
        report.setFirstName(reportDto.getFirstName());
        report.setCounty(reportDto.getCounty());
        report.setAddress(reportDto.getAddress());
        report.setEmail(reportDto.getEmail());
        report.setCaseType(report.getCaseType());
        try {
            report.setNotifiedDate(simpleDateFormat.parse(reportDto.getNotifiedDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        report.setMessage(reportDto.getMessage());
        report.setIsDanger(reportDto.getIsDanger());
        report.setImg(fileName);
        report.setIsAnonym(reportDto.getIsAnonym());
        report.setIpAddress(getClientIp(request));
        report.setCreatedDate(new Date());
        generateTokenAndSendMailToConfirm(report);
        report = save(report);





        try {
            uploadImage(report, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        log.debug("create a new report: " + report);
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

    public Report getReport(long tokenId) {
        return reportJdbcDao.findByToken(tokenId);
    }

    public List<Report> getAllReports() {
        return reportJdbcDao.findAll();

    }

    public String getFileName(MultipartFile multipartFile) {
        return StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

    public void uploadImage(Report savedReport, String fileName, MultipartFile multipartFile) throws IOException {
        String uploadDir = "images/" + savedReport.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    public void generateTokenAndSendMailToConfirm(Report report) {

        if (!report.isActive()) {
            Token token = tokenService.createNew("CONFIRMATION");
            report.setToken(token);
            emailService.sendConfirmationMail(report.getEmail(), token.getToken());
        } else {
            log.debug("Ez egy anonym bejelentés volt, nem szükséges a token létrehozása.");

        }
    }

    public Report setActiveState(Report report,Boolean active){
        report.setActive(active);
        return save(report);
    }

    public void setSuccessfulState(String tokenUuid) {
       Token token = tokenService.getToken(tokenUuid);
        Report report = getReport(token.getId());
        Date currentDate = new Date();

        if(token.isActive() && !token.isSuccessful()){
            tokenService.setActiveAndSuccessfulDate(token,false,true,currentDate);
            setActiveState(report,true);
            log.debug("A megerősítés sikeres volt....");

        }else if(report.isActive()){
            log.debug("A megerősítés már sikeres volt");
        }
        else{
            setActiveState(report,false);
            log.debug("A megerősítés sikertelen volt...");
        }
    }



}
