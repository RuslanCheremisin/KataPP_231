package kata.acad.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import kata.acad.Exception.GlobalExceptionHandler;
import kata.acad.Model.User;
import kata.acad.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String getAllUsers(ModelMap model, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        if (model.containsAttribute("errors")) {
            model.addAttribute("errors", new HashMap<>());
        }
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping(value = "/add", produces = MediaType.TEXT_HTML_VALUE + "; charset=UTF-8")
    public String saveUser(
            @RequestParam @NotBlank @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$") String firstName,
            @RequestParam @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$") String lastName,
            @RequestParam @Positive @Max(120) int age,
            Model model
    ) {
        try {
            User user = new User(firstName, lastName, age);
            userService.addUser(user);
            return "redirect:/users";
        } catch (ConstraintViolationException e) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String field = violation.getPropertyPath().toString();
                errors.put(field, violation.getMessage());
            }

            User user = new User(firstName, lastName, age);

            model.addAttribute("user", user);
            model.addAttribute("errors", errors);
            return "add-user";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam @Positive Long id, Model model) {
        if (!model.containsAttribute("errors")) {
            model.addAttribute("errors", new HashMap<>());
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping(value = "/edit", produces = MediaType.TEXT_HTML_VALUE + "; charset=UTF-8")
    public String updateUser(
            @RequestParam @Positive Long id,
            @RequestParam @NotBlank @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$") String firstName,
            @RequestParam @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$") String lastName,
            @RequestParam @Positive @Max(120) int age,
            Model model
    ) {
        try {
            User user = userService.getUserById(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            userService.updateUser(id, user);
            return "redirect:/users";
        } catch (ConstraintViolationException e) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String field = violation.getPropertyPath().toString();
                errors.put(field, violation.getMessage());
            }

            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);

            model.addAttribute("user", user);
            model.addAttribute("errors", errors);
            return "edit-user";
        }
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam @Positive Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/encoding-test")
    @ResponseBody
    public String testEncoding() {
        return """
           <!DOCTYPE html>
           <html>
           <head>
               <meta charset="UTF-8">
               <title>Тест</title>
           </head>
           <body>
               <h1>Тест кодировки: Добавление пользователя</h1>
           </body>
           </html>
           """;
    }
}




