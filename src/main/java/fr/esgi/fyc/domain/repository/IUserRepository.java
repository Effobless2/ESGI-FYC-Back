package fr.esgi.fyc.domain.repository;

import fr.esgi.fyc.domain.model.User;
import java.util.List;

public interface IUserRepository {

    public void saveUser(User user);
    public List<User> selectAllUsers();
    public User selectUserById(Integer id);
    public User selectUserByEmail(String email);
    public int updateUser(User user);
    public int deleteUser(User user);

}
