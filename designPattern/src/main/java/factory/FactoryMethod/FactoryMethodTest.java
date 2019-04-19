package factory.FactoryMethod;

import factory.entity.Course;

public class FactoryMethodTest {

    public static void main(String[] args) {
        Course course = new JavaCourseFactory().createCourse();
        course.doCourse();
    }
}
