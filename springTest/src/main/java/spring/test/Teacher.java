package spring.test;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Teacher {

    private Integer id;

    private String name;

    public Teacher() {
        generateRandomTeacher();
    }

    private void generateRandomTeacher() {
        Random random = new Random();
        this.id = random.nextInt(500);
        this.name = "王" + random.nextInt(10);
    }

    @Override
    public String toString() {
        return "id=" + id + ",name=" + name;
    }
}
