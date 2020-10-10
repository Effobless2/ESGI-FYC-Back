package fr.esgi.fyc.persistence.parsers;

import fr.esgi.fyc.DTO.UserGetDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserGetDTO>{
    @Override
    public UserGetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserGetDTO user = new UserGetDTO();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setRole(rs.getString("role"));

        return user;
    }

}
