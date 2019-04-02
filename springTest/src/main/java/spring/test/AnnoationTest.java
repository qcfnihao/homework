package spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnoationTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(Student.class, Teacher.class);
        annotationConfigApplicationContext.refresh();

        Student student = (Student) annotationConfigApplicationContext.getBean("student");

        System.out.println(student.getTeacher().toString());

        annotationConfigApplicationContext.scan("spring.test");
        annotationConfigApplicationContext.refresh();
    }
}
