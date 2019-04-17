package factory.abstractFactory;

public class PythonCourseFactory implements  CourseFactory {
    public Video createVideo() {
        return new PythonVideo();
    }

    public Note createNote() {
        return new PythonNote();
    }
}
