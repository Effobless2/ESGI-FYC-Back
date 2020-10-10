package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IUserRepository;
import fr.esgi.fyc.infrastructure.persistence.parsers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDAO extends JdbcDaoSupport implements IUserRepository {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void saveUser(fr.esgi.fyc.domain.model.User user) {

        final String USER_INSERT = "INSERT INTO users (login, password, firstName, LastName, email, role) values (?,?,?,?,?,?)";

        getJdbcTemplate().update(USER_INSERT,
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public List<User> selectAllUsers(){
        final String USER_GET_ALL = "SELECT id, login, firstName, LastName, email, role FROM users";
        List<User> users = getJdbcTemplate().query(
                USER_GET_ALL,
                new UserRowMapper());

        return users;
    }

    @Override
    public fr.esgi.fyc.domain.model.User selectUserById(Integer id){
        final String USER_GET_BY_ID = "SELECT id, login, firstName, LastName, email, role FROM users AS u WHERE u.id = ?";
        return getJdbcTemplate().queryForObject(USER_GET_BY_ID, new Object[]{id}, new UserRowMapper());
    }

    @Override
    public User selectUserByEmail(String email){
        final String USER_GET_BY_EMAIL = "SELECT id, login, firstName, LastName, email, role FROM users AS u WHERE u.email = ?";
        return getJdbcTemplate().queryForObject(USER_GET_BY_EMAIL, new Object[]{email}, new UserRowMapper());
    }

}