package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Deprecated
    @Override
    protected void checkOrder() {
        List<Resume> resumes = storage.getAllSorted();
        int expectedLength = 4;
        Assertions.assertAll("Deleted u3 in abstractClass",
                () -> assertEquals(new Resume("u1", TEST_FULL_NAME + 1), resumes.get(0)),
                () -> assertEquals(new Resume("u9", TEST_FULL_NAME + 9), resumes.get(1)),
                () -> assertEquals(new Resume("u5", TEST_FULL_NAME + 5), resumes.get(2)),
                () -> assertEquals(new Resume("u7", TEST_FULL_NAME + 7), resumes.get(3)),
                () -> assertEquals(expectedLength, storage.getSize())
        );
    }
}
