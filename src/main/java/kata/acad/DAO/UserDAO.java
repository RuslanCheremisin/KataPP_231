package kata.acad.DAO;

import kata.acad.Model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void updateUser(User user);
    List<User> getAllUsers();
    User getUserByID(int id);
    void deleteUser(User user);
    void deleteAllUsers();


}
