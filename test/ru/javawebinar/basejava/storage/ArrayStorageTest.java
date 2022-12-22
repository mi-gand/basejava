package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    private Storage storage = new ArrayStorage();

    public ArrayStorageTest() {
        super();
    }

    @Override
    protected void checkOrder() {
        Resume[] resumes = storage.getAll();
        int expectedLength = 4;
        Assertions.assertAll("Deleted u3 in abstractClass",
                ()->assertEquals(new Resume("u1"), resumes[0]),
                ()->assertEquals(new Resume("u2"), resumes[1]),
                ()->assertEquals(new Resume("u5"), resumes[2]),
                ()->assertEquals(new Resume("u4"), resumes[3]),
                ()->assertEquals(expectedLength, storage.getSize())
        );
    }
}
