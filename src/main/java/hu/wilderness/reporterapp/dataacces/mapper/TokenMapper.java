package hu.wilderness.reporterapp.dataacces.mapper;

import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.utils.DateHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {

    @Override
    public Token mapRow(ResultSet rs, int rowNum) throws SQLException {

        Token token = new Token();
        token.setId(rs.getLong("id"));
        token.setActive(rs.getBoolean("active"));
        token.setSuccessful(rs.getBoolean("successful"));
        try {token.setType(Token.TokenType.valueOf(rs.getString("type")));} catch (Exception e) {}
        token.setToken(rs.getString("token"));
        token.setCreatedAt(DateHelper.getDateTime(rs, "created_at"));
        token.setExpiresAt(DateHelper.getDateTime(rs, "expires_at"));
        token.setConfirmedAt(DateHelper.getDateTime(rs, "confirmed_at"));
        token.setUser(getUserObject(rs,""));
//           token.setUser(new User(rs.getLong("user_id")));

        return token;
    }

    private static User getUserObject(ResultSet rs, String prefix) {
        try {
            long userId = rs.getLong("user_id");
            if(rs.wasNull()) return null;
            return new User(userId);
        } catch (SQLException e) {
            return null;
        }
    }
}
