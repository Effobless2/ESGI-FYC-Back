package fr.esgi.fyc.domain.repository;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.infrastructure.web.DTO.AuthDTO;

import java.util.List;

public interface IUserRepository {

    public int saveUser(User user);
    public List<User> selectAllUsers();
    public User selectUserById(Integer id);
    public User selectUserByEmail(String email);
    public int updateUser(User user);
    public int updateUserPassword(AuthDTO authDTO);
    public int deleteUser(User user);

}
