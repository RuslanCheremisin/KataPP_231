package kata.acad.Service.Impl;

import kata.acad.DAO.UserDAO;
import kata.acad.Model.User;
import kata.acad.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceNoSpringBootImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user) {
        userDAO.updateUser(id, user);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDAO.getUserByID(id);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Transactional
    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }



}
