package fr.esgi.fyc.domain.services;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserDAO;

    public void add(User user){
        iUserDAO.saveUser(user);
    }

    public List<User> getAll() { return iUserDAO.selectAllUsers(); }

    public User getById(Integer id) { return iUserDAO.selectUserById(id); }

    public User getByEmail(String email) { return iUserDAO.selectUserByEmail(email); }

    public int updateUser(User user) { return  iUserDAO.updateUser(user); }

}
