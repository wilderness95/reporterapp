package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.TokenJdbcDao;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.utils.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static hu.wilderness.reporterapp.domain.Token.TokenType.CONFIRMATION;

@Service
public class TokenService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenJdbcDao tokenJdbcDao;

    public Token createNew() {
        Token token = new Token();
        token.setToken(createNewToken());
        token.setActive(true);
        token.setSuccessful(false);
        token.setCreatedAt(new Date());
        token.setExpiresAt(DateHelper.addigHoursToDate(new Date(), 4));
        token.setType(CONFIRMATION);
        token = save(token);
        log.debug("create a new " + token.getType() + " token: " + token);

        return token;

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
            log.debug(token.getType() + " token has been successfully updated...");
        }
        return token;
    }

    public Boolean isExpired(Token token) {
        Date currentDate = new Date();
        if (currentDate.before(token.getExpiresAt())) {
            log.debug("A token még érvényességi időn belül van...");
            return true;
        } else {
            log.debug("A token érvényessége lejárt...");
            return false;
        }
    }
}
