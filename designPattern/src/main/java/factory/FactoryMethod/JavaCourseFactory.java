package factory.FactoryMethod;

import factory.entity.Course;
import factory.entity.JavaCourse;

public class JavaCourseFactory implements  CourseFactory {
    public Course createCourse() {
        return new JavaCourse();
    }
}
