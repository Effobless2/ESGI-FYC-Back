package fr.esgi.fyc.persistence.DAO;

import fr.esgi.fyc.DTO.UserGetDTO;
import fr.esgi.fyc.domain.model.User;

import java.util.List;

public interface IUserDAO {

    public void saveUser(User user);
    public List<UserGetDTO> selectAllUsers();
    public UserGetDTO selectUserById(Integer id);
    public UserGetDTO selectUserByEmail(String email);

}
