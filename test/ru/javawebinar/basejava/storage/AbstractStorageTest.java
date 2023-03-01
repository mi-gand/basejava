package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;


public abstract class AbstractStorageTest {

    protected final static String UUID_1 = "u1";
    protected final static String UUID_3 = "u3";
    protected final static String UUID_5 = "u5";
    protected final static String UUID_7 = "u7";
    protected final static String UUID_9 = "u9";
    protected final static String UUID_NOT_EXIST = "dummy";
    public final static String TEST_FULL_NAME = "test_full_name_";
    protected final static Resume RESUME_1 = new Resume(UUID_1, TEST_FULL_NAME + 1);
    protected final static Resume RESUME_3 = new Resume(UUID_3, TEST_FULL_NAME + 3);
    protected final static Resume RESUME_5 = new Resume(UUID_5, TEST_FULL_NAME + 5);
    protected final static Resume RESUME_7 = new Resume(UUID_7, TEST_FULL_NAME + 7);
    protected final static Resume RESUME_9 = new Resume(UUID_9, TEST_FULL_NAME + 9);
    protected final Storage storage;

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
        Assertions.assertTrue(storage.getAllSorted().isEmpty());
    }

    @Test
    void testUpdate() {
        Resume resume3 = new Resume(UUID_3, TEST_FULL_NAME + 3);
        storage.update(resume3);
        Assertions.assertEquals(storage.get(UUID_3), resume3);
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    Resume notExist = new Resume(UUID_NOT_EXIST, TEST_FULL_NAME + 0);
                    storage.update(notExist);
                }
        );
    }

    @Test
    void testSave() {
        Resume r10 = new Resume("u10", TEST_FULL_NAME + 10);
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
        Assertions.assertTrue(assertGet(new Resume(UUID_1, TEST_FULL_NAME + 1)));
        Assertions.assertTrue(assertGet(new Resume(UUID_3, TEST_FULL_NAME + 3)));
        Assertions.assertTrue(assertGet(new Resume(UUID_5, TEST_FULL_NAME + 5)));
        Assertions.assertTrue(assertGet(new Resume(UUID_7, TEST_FULL_NAME + 7)));
        Assertions.assertThrows(NotExistStorageException.class,
                () -> assertGet(new Resume(UUID_NOT_EXIST, TEST_FULL_NAME + 0)
                )
        );
    }

    @Test
    void testGetAll() {
        List<Resume> resumes = storage.getAllSorted();
        List<Resume> expectedResumes = initResumeArray();
        Assertions.assertTrue(assertSize(5));
    }

    List<Resume> initResumeArray() {
        return List.of(
                new Resume(UUID_1, TEST_FULL_NAME + 1),
                new Resume(UUID_3, TEST_FULL_NAME + 3),
                new Resume(UUID_5, TEST_FULL_NAME + 5),
                new Resume(UUID_7, TEST_FULL_NAME + 7),
                new Resume(UUID_9, TEST_FULL_NAME + 9)
        );
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