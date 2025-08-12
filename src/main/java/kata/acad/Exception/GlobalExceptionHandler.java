package kata.acad.Exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import kata.acad.Model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    static public Map<String, String> processValidationErrors(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {

            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            if (fieldName.startsWith("arg")) {

                int paramIndex = Integer.parseInt(fieldName.substring(3));
                fieldName = getParameterNameForIndex(paramIndex);
            } else if (propertyPath.contains("saveUser") || propertyPath.contains("updateUser")) {

                fieldName = fieldName.toLowerCase();
            }
            errors.put(fieldName, violation.getMessage());
        }
        return errors;
    }

    private static String getParameterNameForIndex(int index) {
        switch(index) {
            case 0: return "firstName";
            case 1: return "lastName";
            case 2: return "age";
            case 3: return "id";
            default: return "param" + index;
        }
    }

    @ExceptionHandler(AddUserException.class)
    static public String handleAddUserViolation(ConstraintViolationException ex, Model model) {
        Map<String, String> errors = processValidationErrors(ex);
        model.addAttribute("errors", errors);
        User user = new User();
        model.addAttribute("user", user);

        return "add-user";
    }

    @ExceptionHandler(EditUserException.class)
    static public String handleEditUserViolation(ConstraintViolationException ex, Model model) {
        Map<String, String> errors = processValidationErrors(ex);
        model.addAttribute("errors", errors);

        User user = new User();
        model.addAttribute("user", user);

        return "edit-user";
    }
}