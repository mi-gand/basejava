package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractStorageTest {

    protected final Storage storage;
    protected final static String UUID_1 = "u1";
    protected final static String UUID_3 = "u3";
    protected final static String UUID_5 = "u5";
    protected final static String UUID_7 = "u7";
    protected final static String UUID_9 = "u9";
    protected final static String UUID_NOT_EXIST = "dummy";

    protected final static Resume RESUME_1 = new Resume(UUID_1);
    protected final static Resume RESUME_3 = new Resume(UUID_3);
    protected final static Resume RESUME_5 = new Resume(UUID_5);
    protected final static Resume RESUME_7 = new Resume(UUID_7);
    protected final static Resume RESUME_9 = new Resume(UUID_9);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void init() {
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_5);
        storage.save(RESUME_7);
        storage.save(RESUME_9);
    }

    @AfterEach
    void clearAfterTest() {
        storage.clear();
    }

    @Test
    void testClear() {
        storage.clear();
        Resume[] allResumes = storage.getAll();
        Assertions.assertAll("Check field \"size\" and array length",
                () -> assertSize(0),
                () -> assertEquals(0, allResumes.length)
        );
    }

    @Test
    void testUpdate() {
        Resume resume3 = new Resume(UUID_3);
        storage.update(resume3);
        Assertions.assertEquals(storage.get(UUID_3), resume3);
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    Resume notExist = new Resume(UUID_NOT_EXIST);
                    storage.update(notExist);
                }
        );
    }

    @Test
    void testSave() {
        Resume r10 = new Resume("u10");
        storage.save(r10);
        Assertions.assertTrue(assertGet(r10));
        Assertions.assertTrue(assertSize(6));
        Assertions.assertThrows(ExistStorageException.class,
                () -> {
                    storage.save(r10);
                }
        );
    }

    @Test
    void testGet() {
        Assertions.assertTrue(assertGet(new Resume(UUID_1)));
        Assertions.assertTrue(assertGet(new Resume(UUID_3)));
        Assertions.assertTrue(assertGet(new Resume(UUID_5)));
        Assertions.assertTrue(assertGet(new Resume(UUID_7)));
        Assertions.assertThrows(NotExistStorageException.class,
                () -> assertGet(new Resume(UUID_NOT_EXIST)
                )
        );
    }

    @Test
    void testGetAll() {
        Resume[] resumes = storage.getAll();
        Resume[] expectedResumes = initResumeArray();
        Assertions.assertArrayEquals(expectedResumes, resumes);
        Assertions.assertTrue(assertSize(5));
    }

    Resume[] initResumeArray() {
        return new Resume[]{
                new Resume(UUID_1),
                new Resume(UUID_3),
                new Resume(UUID_5),
                new Resume(UUID_7),
                new Resume(UUID_9),
        };
    }

    boolean assertGet(Resume resume) {
        return storage.get(resume.getUuid()).equals(resume);
    }

    boolean assertSize(int expectedSize) {
        return expectedSize == storage.getSize();
    }

    @Test
    void testGetSize() {
        Assertions.assertTrue(assertSize(5));
    }
}
