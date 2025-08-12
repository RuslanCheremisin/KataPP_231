package kata.acad.Model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Pattern;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Имя не может быть пустым!")
    @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$", message = "Можно использовать только буквы и дефисы(для составных имён)!")
    private String firstName;
    @Column
    @Pattern(regexp = "^[\\p{L}'-]+(?:\\s[\\p{L}'-]+)*$", message = "Можно использовать только буквы и дефисы(для составных фамилий)!")
    private String lastName;
    @Column(nullable = false)
    @Positive(message = "Возраст должен быть больше нуля!")
    @Max(value = 120, message = "Возраст должен быть больше возраст должен быть меньше 120!")
    private int age;

    public User() {
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public void setId(@Positive Long id) {
        this.id = id;
    }
}
