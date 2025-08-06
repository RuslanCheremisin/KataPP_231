package kata.acad.Service;

import kata.acad.Model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(Long id, User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    void deleteAllUsers();


}
