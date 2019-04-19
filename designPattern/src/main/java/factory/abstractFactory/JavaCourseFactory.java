package factory.abstractFactory;

public class JavaCourseFactory implements  CourseFactory {
    public Video createVideo() {
        return new JavaVideo();
    }

    public Note createNote() {
        return new JavaNote();
    }
}
