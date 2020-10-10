package fr.esgi.fyc.DAO;

import fr.esgi.fyc.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDAO extends JdbcDaoSupport implements IUserDAO{

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void saveUser(User user) {

        final String USER_INSERT = "insert into users (login, password, firstName, LastName, email, role) values (?,?,?,?,?,?)";

        getJdbcTemplate().update(USER_INSERT,
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

}