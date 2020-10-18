package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IUserRepository;
import fr.esgi.fyc.infrastructure.persistence.parsers.UserRowMapper;
import fr.esgi.fyc.infrastructure.web.DTO.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public int saveUser(User user) {
      try{
        final String USER_INSERT = "INSERT INTO users (password, firstName, lastName, email, role) values (?,?,?,?,?)";

        getJdbcTemplate().update(USER_INSERT,
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
        return getJdbcTemplate().queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

      }catch (EmptyResultDataAccessException e) {
        System.out.println("ERROR IN USERDAO.SELECT_USER_BY_ID : " + e);
        return -1;
      }
    }

    @Override
    public List<User> selectAllUsers(){
        final String USER_GET_ALL = "SELECT id, firstName, lastName, email, password, role FROM users";
        List<User> users = getJdbcTemplate().query(
                USER_GET_ALL,
                new UserRowMapper());
        return users;
    }

    @Override
    public User selectUserById(Integer id){
        final String USER_GET_BY_ID = "SELECT id, firstName, lastName, email, password, role FROM users AS u WHERE u.id = ?";
        try{
            return getJdbcTemplate().queryForObject(USER_GET_BY_ID, new Object[]{id}, new UserRowMapper());
        }catch (EmptyResultDataAccessException e) {
            System.out.println("ERROR IN USERDAO.SELECT_USER_BY_ID : " + e);
            return null;
        }
    }

    @Override
    public User selectUserByEmail(String email){
        final String USER_GET_BY_EMAIL = "SELECT id, firstName, lastName, email, password, role FROM users AS u WHERE u.email = ?";
        try{
            return getJdbcTemplate().queryForObject(USER_GET_BY_EMAIL, new Object[]{email}, new UserRowMapper());
        }catch (EmptyResultDataAccessException e) {
            System.out.println("ERROR IN USERDAO.SELECT_USER_BY_EMAIL : " + e);
            return null;
        }
    }

    @Override
    public int updateUser(User user){
        final String USER_UPDATE = "UPDATE users AS u SET u.firstName = ?, u.lastName = ?, u.role = ? WHERE u.id = ?";
        try{
            return getJdbcTemplate().update(USER_UPDATE,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole(),
                    user.getId()
            );
        }catch (RuntimeException e) {
            System.out.println("ERROR IN USERDAO.UPDATE_USER : " + e);
            return 0;
        }
    }

    @Override
    public int updateUserPassword(AuthDTO authDTO){
      final String USER_UPDATE_PASSWORD = "UPDATE users AS u SET u.password = ? WHERE u.id = ?";
      try{
        return getJdbcTemplate().update(USER_UPDATE_PASSWORD,
          authDTO.getPassword(),
          authDTO.getId()
        );
      }catch (RuntimeException e) {
        System.out.println("ERROR IN USERDAO.UPDATE_USER_PASSWORD : " + e);
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
