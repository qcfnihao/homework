package factory.abstractFactory;

public class AbstractFactoryTest {

    public static void main(String[] args) {
        CourseFactory javaCourseFactory = new JavaCourseFactory();

        javaCourseFactory.createNote().doNote();

        javaCourseFactory.createVideo().doVideo();
    }
}
