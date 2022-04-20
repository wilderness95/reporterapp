package hu.wilderness.reporterapp.dataacces.mapper;

import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.utils.DateMapperHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {

    @Override
    public Token mapRow(ResultSet rs, int rowNum) throws SQLException {

        Token token = new Token();
        token.setId(rs.getLong("id"));
        token.setActive(rs.getBoolean("active"));
        try {token.setType(Token.TokenType.valueOf(rs.getString("type")));} catch (Exception e) {}
        token.setToken(rs.getString("token"));
        token.setCreatedAt(DateMapperHelper.getDateTime(rs, "created_date"));
        token.setExpiresAt(DateMapperHelper.getDateTime(rs, "expires_date"));
        token.setConfirmedAt(DateMapperHelper.getDateTime(rs, "confirmed_date"));
        return token;
    }
}
