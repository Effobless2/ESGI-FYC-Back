package fr.esgi.fyc.DAO;

import fr.esgi.fyc.domain.model.User;

public interface IUserDAO {

    public User SelectUserById(Integer id);
    public User SelectUserByEmail(String email);
    public void saveUser(User user);
    //public List<User> getAllUsers();

}
