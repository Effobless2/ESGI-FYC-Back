package fr.esgi.fyc.services;

import fr.esgi.fyc.persistence.DAO.IUserDAO;
import fr.esgi.fyc.DTO.UserGetDTO;
import fr.esgi.fyc.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserDAO iUserDAO;

    public void add(User user){
        iUserDAO.saveUser(user);
    }

    public List<UserGetDTO> getAll() { return iUserDAO.selectAllUsers(); }

    public UserGetDTO getById(Integer id) { return iUserDAO.selectUserById(id); }

    public UserGetDTO getByEmail(String email) { return iUserDAO.selectUserByEmail(email); }

}
