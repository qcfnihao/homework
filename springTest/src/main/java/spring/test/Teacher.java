package spring.test;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;

@Component
public class Teacher implements Serializable {

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
