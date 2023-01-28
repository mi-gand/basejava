package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.*;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_CAPACITY;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

/*    @Test
    void testClear() {
        storage.clear();
        Resume[] allResumes = storage.getAll();
        Assertions.assertAll("Check field \"size\" and array length",
                () -> assertSize(0),
                () -> assertEquals(0, allResumes.length)
        );
    }*/

    @Test
    void testSaveOverFlow() {
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
    void testDelete() {
        storage.delete(UUID_3);
        Assertions.assertTrue(assertSize(4));
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    storage.delete(UUID_3);
                }
        );
        checkOrder();
    }

/*    @Test
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
    }*/

    @Deprecated
    protected abstract void checkOrder();
}