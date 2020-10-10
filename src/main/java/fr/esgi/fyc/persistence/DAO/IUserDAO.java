package fr.esgi.fyc.persistence.DAO;

import fr.esgi.fyc.DTO.UserGetDTO;
import fr.esgi.fyc.domain.model.User;

public interface IUserDAO {

    public UserGetDTO SelectUserById(Integer id);
    public UserGetDTO SelectUserByEmail(String email);
    public void saveUser(User user);
    //public List<User> getAllUsers();

}
