package factory.FactoryMethod;

import factory.entity.Course;
import factory.entity.PythonCourse;

public class PythonCourseFactory implements  CourseFactory {
    public Course createCourse() {
        return new PythonCourse();
    }
}
