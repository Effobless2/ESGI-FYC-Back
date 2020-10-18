package fr.esgi.fyc.domain.services;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IUserRepository;
import fr.esgi.fyc.infrastructure.web.DTO.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    public void add(User user){
      iUserRepository.saveUser(user);
    }

    public List<User> getAll() { return iUserRepository.selectAllUsers(); }

    public User getById(Integer id) { return iUserRepository.selectUserById(id); }

    public User getByEmail(String email) { return iUserRepository.selectUserByEmail(email); }

    public int updateUser(User user) { return  iUserRepository.updateUser(user); }

    public  int deleteUser(User user) { return iUserRepository.deleteUser(user); }

    public int updateUserPassword(AuthDTO authDTO) { return  iUserRepository.updateUserPassword(authDTO); }

}
