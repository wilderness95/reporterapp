package hu.wilderness.reporterapp.service;

import hu.wilderness.reporterapp.dataacces.dao.ReportJdbcDao;
import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.ReportDto;
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
import java.util.Date;
import java.util.UUID;

@Service
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String pattern = "yyyy-MM-dd";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    @Autowired
    ReportJdbcDao reportJdbcDao;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenService tokenService;

    public Report createNew(ReportDto reportDto, HttpServletRequest request,MultipartFile multipartFile) {
        String fileName = getFileName(multipartFile);
        Report report = new Report();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(reportDto, report);

        report.setActive(reportDto.getIsAnonym() ?  true : false);
        report.setLastName(reportDto.getLastName());
        report.setFirstName(reportDto.getFirstName());
        report.setCounty(reportDto.getCounty());
        report.setAddress(reportDto.getAddress());
        report.setEmail(reportDto.getEmail());
        report.setCaseType(report.getCaseType());
        try {report.setNotifiedDate(simpleDateFormat.parse(reportDto.getNotifiedDate()));} catch (ParseException e) {e.printStackTrace();}
        report.setMessage(reportDto.getMessage());
        report.setIsDanger(reportDto.getIsDanger());
        report.setImg(fileName);
        report.setIsAnonym(reportDto.getIsAnonym());
        report.setIpAddress(getClientIp(request));
        report.setCreatedDate(new Date());
        generateTokenAndSendMailToConfirm(report);

        report = save(report);

        try {
            uploadImage(report, fileName,multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }



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

    public String getFileName(MultipartFile multipartFile){
        return StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

    public void uploadImage(Report savedReport,String fileName, MultipartFile multipartFile) throws IOException {
        String uploadDir = "images/" + savedReport.getId();
        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
    }

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    public void generateTokenAndSendMailToConfirm(Report report){


            if (!report.isActive()){
                Token token = tokenService.createNew();
                report.setToken(token);
                emailService.sendConfirmationMail(report.getEmail(), token.getToken());
            }else {
            log.debug("Ez egy anonym bejelentés volt, nem szükséges a token létrehozása.");

        }


    }
}
