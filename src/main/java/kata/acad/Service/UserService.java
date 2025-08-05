package kata.acad.Service;

import kata.acad.Model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    void deleteUser(User user);

    void deleteAllUsers();

}
