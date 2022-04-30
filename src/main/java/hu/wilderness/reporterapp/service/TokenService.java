package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.TokenJdbcDao;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.exception.ErrorConstant;
import hu.wilderness.reporterapp.exception.ReporterException;
import hu.wilderness.reporterapp.utils.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static hu.wilderness.reporterapp.domain.Token.TokenType.*;

@Service
public class TokenService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenJdbcDao tokenJdbcDao;

    public Token createNew(String type, User user) {
        Token token = new Token();
        token.setToken(createNewToken());
        token.setActive(true);
        token.setSuccessful(false);
        token.setCreatedAt(new Date());
        token.setExpiresAt(DateHelper.addigHoursToDate(new Date(), 4));
        if (type.equals("CONFIRMATION")) {
            token.setType(Token.TokenType.CONFIRMATION);
        } else if (type.equals("FIRSTPASSWORD")) {
            token.setType(Token.TokenType.FIRSTPASSWORD);
        } else if (type.equals("RESETPASSWORD")) {
            token.setType(Token.TokenType.RESETPASSWORD);
        }
        if (user != null) {
            token.setUser(user);
            log.debug("\n \n"+user.toString()+"\n\n");
        }
        token = save(token);
        log.debug("create a new " + token.getType() + " token: " + token);

        return token;

    }

    public Token setActiveAndSuccessfulDate(Token token, Boolean active, Boolean successful, Date currentDate) {
        token.setActive(active);
        token.setSuccessful(successful);
        token.setConfirmedAt(currentDate);
        return save(token);
    }


    private String createNewToken() {
        return UUID.randomUUID().toString();
    }

    public Token save(Token token) {
        if (token.getId() == null) {
            token = tokenJdbcDao.insert(token);
            log.debug(token.getType() + " token has been successfully saved...");
        } else {
            token = tokenJdbcDao.update(token);
            log.debug(token.getType() + " token has been successfully updated..." + token.toString());
        }
        return token;
    }
public Token getToken2(String tokeuid){
        return tokenJdbcDao.findByTokenAndActive(tokeuid,true);

}
    public Token getToken(String tokenUuid) {
        Token token = tokenJdbcDao.findByToken(tokenUuid);
        System.out.println("fasz bele " + token.toString());
        if (isNotExpiredAndActive(token) && !token.isSuccessful())
            return token;
        else if (!isNotExpiredAndActive(token) && token.isSuccessful()) {
            //throw  new ReporterException(ErrorConstant.TOKEN_IS_ALREADY_ACTIVATED);
            return token;
        } else {
            return setActiveAndSuccessfulDate(token, false, false, new Date());
            //  throw new ReporterException(ErrorConstant.TOKEN_IS_EXPIRED);
        }
    }



    public Token getActiveToken(String tokenUuid) {
        return tokenJdbcDao.findByTokenAndActive(tokenUuid, true);
    }

    public Boolean isNotExpiredAndActive(Token token) {
        Date currentDate = new Date();
        if (currentDate.before(token.getExpiresAt()) && token.isActive()) {
            log.debug("A token még érvényességi időn belül van és aktív...");
            return true;
        } else {
            log.debug("A token érvényessége lejárt...");
            return false;
        }
    }


}
