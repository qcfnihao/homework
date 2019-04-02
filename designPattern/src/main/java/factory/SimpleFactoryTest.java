package factory;

import factory.entity.Course;
import factory.entity.JavaCourse;

public class SimpleFactoryTest {

    public static void main(String[] args) {
        Course course = SimpleFactory.createCourse("java");
        course.doCourse();
        Course course1 = SimpleFactory.createCourseForReflect("factory.entity.JavaCourse");
        course1.doCourse();
        Course course2 = SimpleFactory.createCourseForReflect(JavaCourse.class);
        course2.doCourse();
    }
}
