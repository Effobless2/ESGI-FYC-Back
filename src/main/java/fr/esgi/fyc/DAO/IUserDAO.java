package fr.esgi.fyc.DAO;

import fr.esgi.fyc.domain.model.User;

public interface IUserDAO {

    //public User getUserUserById(int id);
    //public User getUserByLogin(String login);
    public void saveUser(User user);
    //public List<User> getAllUsers();

}
