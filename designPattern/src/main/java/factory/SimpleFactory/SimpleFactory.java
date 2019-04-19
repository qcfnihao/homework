package factory.SimpleFactory;

import factory.entity.Course;
import factory.entity.JavaCourse;
import factory.entity.PythonCourse;

import java.util.Calendar;


/**
 * 简单工厂   违背了开闭原则  工厂职责过重，不利于复杂的产品结构的扩展
 **/
public class SimpleFactory {


    public static Course createCourse(String name) {
        if ("java".equals(name)) {
            return new JavaCourse();
        } else if ("python".equals(name)) {
            return new PythonCourse();
        }
        return null;
    }

    public static Course createCourseForReflect(String className) {

        try {
            if (className != null && className != "") {
                return (Course) Class.forName(className).newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Course createCourseForReflect(Class<?> clazz) {

        try {
            if (clazz != null) {
                return (Course) clazz.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
