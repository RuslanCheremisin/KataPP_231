package kata.acad.Controller;

import kata.acad.Model.User;
import kata.acad.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String saveUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int age
    ) {
        User user = new User(firstName, lastName, age);
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit")
    public String updateUser(
            @RequestParam Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int age
    ) {
        User user = userService.getUserById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}




