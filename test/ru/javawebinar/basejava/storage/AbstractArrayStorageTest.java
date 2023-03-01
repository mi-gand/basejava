package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_CAPACITY;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void testSaveOverFlow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_CAPACITY; i++) {
                Resume r = new Resume("u" + i, TEST_FULL_NAME);
                storage.save(r);
            }
        } catch (StorageException e) {
            System.out.println(storage.getSize());
            fail("Overflow occurred ahead of time");
        }
        Assertions.assertThrows(StorageException.class,
                () -> {
                    storage.save(new Resume("u10001", TEST_FULL_NAME));
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

    private void checkOrder() {
        List<Resume> resumes = storage.getAllSorted();
        int expectedLength = 4;
        Assertions.assertAll("Deleted u3 in abstractClass",
                () -> assertEquals(new Resume("u1", TEST_FULL_NAME + 1), resumes.get(0)),
                () -> assertEquals(new Resume("u5", TEST_FULL_NAME + 5), resumes.get(1)),
                () -> assertEquals(new Resume("u7", TEST_FULL_NAME + 7), resumes.get(2)),
                () -> assertEquals(new Resume("u9", TEST_FULL_NAME + 9), resumes.get(3)),
                () -> assertEquals(expectedLength, storage.getSize())
        );
    }
}