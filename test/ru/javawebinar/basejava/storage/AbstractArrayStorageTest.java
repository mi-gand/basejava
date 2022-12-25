package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.*;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_CAPACITY;

public abstract class AbstractArrayStorageTest {
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

    protected AbstractArrayStorageTest(Storage storage) {
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
    void clear() {
        storage.clear();
        Resume[] allResumes = storage.getAll();
        Assertions.assertAll("Check field \"size\" and array length",
                () -> assertSize(0),
                () -> assertEquals(0, allResumes.length)
        );
    }

    @Test
    void update() {
        Resume resume3 = new Resume(UUID_3);
        storage.update(resume3);
        Assertions.assertSame(storage.get(UUID_3), resume3);
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    Resume notExist = new Resume(UUID_NOT_EXIST);
                    storage.update(notExist);
                }
        );
    }

    @Test
    void save() {
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
    void saveOverFlow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_CAPACITY; i++) {
                Resume r = new Resume("u" + i);
                storage.save(r);
            }
        } catch (StorageException e) {
            System.out.println(storage.getSize());
            fail("Overflow occurred ahead of time");
        }
        Assertions.assertThrows(StorageException.class,
                () -> {
                    storage.save(new Resume("u10001"));
                }
        );
    }

    @Test
    void get() {
        Assertions.assertTrue(assertGet(new Resume(UUID_1)));
        Assertions.assertTrue(assertGet(new Resume(UUID_3)));
        Assertions.assertTrue(assertGet(new Resume(UUID_5)));
        Assertions.assertTrue(assertGet(new Resume(UUID_7)));
        Assertions.assertThrows(NullPointerException.class,
                () -> assertGet(new Resume(UUID_NOT_EXIST)
                )
        );
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        Assertions.assertTrue(assertSize(4));
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    storage.delete(UUID_3);
                }
        );
        checkOrder();
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        Resume[] expectedResumes = initResumeArray();
        Assertions.assertArrayEquals(expectedResumes, resumes);
        Assertions.assertTrue(assertSize(5));
    }

    @Test
    void getSize() {
        Assertions.assertTrue(assertSize(5));
    }

    boolean assertSize(int expectedSize) {
        return expectedSize == storage.getSize();
    }

    boolean assertGet(Resume resume) throws NullPointerException {
        return storage.get(resume.getUuid()).equals(resume);
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

    @Deprecated
    protected abstract void checkOrder();
}