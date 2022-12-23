package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Resume resume = new Resume("u2");
        Class c = Resume.class;
        Method toStringMethod = c.getMethod("toString");
        toStringMethod.setAccessible(true);
        System.out.println(toStringMethod.invoke(resume));
    }
}
