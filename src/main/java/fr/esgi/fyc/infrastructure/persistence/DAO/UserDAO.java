package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IUserRepository;
import fr.esgi.fyc.infrastructure.persistence.parsers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
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
    public void saveUser(User user) {

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
    public User selectUserById(Integer id){
        final String USER_GET_BY_ID = "SELECT id, login, firstName, LastName, email, role FROM users AS u WHERE u.id = ?";
        try{
            return getJdbcTemplate().queryForObject(USER_GET_BY_ID, new Object[]{id}, new UserRowMapper());
        }catch (EmptyResultDataAccessException e) {
            System.out.println("ERROR IN USERDAO.SELECT_USER_BY_ID : " + e);
            return null;
        }
    }

    @Override
    public User selectUserByEmail(String email){
        final String USER_GET_BY_EMAIL = "SELECT id, login, firstName, LastName, email, role FROM users AS u WHERE u.email = ?";
        try{
            return getJdbcTemplate().queryForObject(USER_GET_BY_EMAIL, new Object[]{email}, new UserRowMapper());
        }catch (EmptyResultDataAccessException e) {
            System.out.println("ERROR IN USERDAO.SELECT_USER_BY_EMAIL : " + e);
            return null;
        }
    }

    @Override
    public int updateUser(User user){
        final String USER_UPDATE = "UPDATE users AS u SET u.firstName = ?, u.LastName = ?, u.password = ?, u.role = ? WHERE u.id = ?";
        try{
            return getJdbcTemplate().update(USER_UPDATE,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword(),
                    user.getRole(),
                    user.getId()
            );
        }catch (RuntimeException e) {
            System.out.println("ERROR IN USERDAO.UPDATE_USER : " + e);
            return 0;
        }
    }

    @Override
    public int deleteUser(User user){
        final String USER_DELETE = "DELETE FROM users AS u WHERE u.id = ?";
        try{
            return getJdbcTemplate().update(USER_DELETE, user.getId());
        }catch (RuntimeException e) {
            System.out.println("ERROR IN USERDAO.USER_DELETE : " + e);
            return 0;
        }
    }

}