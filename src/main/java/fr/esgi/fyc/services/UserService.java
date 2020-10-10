package fr.esgi.fyc.services;

import fr.esgi.fyc.DAO.IUserDAO;
import fr.esgi.fyc.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserDAO iUserDAO;

    public void add(User user){
        iUserDAO.saveUser(user);
    }

    public User getByEmail(String email) { return iUserDAO.SelectUserByEmail(email); }

    public User getById(Integer id) { return iUserDAO.SelectUserById(id); }

}
