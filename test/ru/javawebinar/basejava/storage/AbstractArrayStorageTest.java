package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    @BeforeEach
    void init() {
        Resume r1 = new Resume("u1");
        Resume r3 = new Resume("u3");
        Resume r5 = new Resume("u5");
        Resume r7 = new Resume("u7");
        Resume r9 = new Resume("u9");
        storage.save(r1);
        storage.save(r3);
        storage.save(r5);
        storage.save(r7);
        storage.save(r9);
    }

    @AfterEach
    void clearAfterTest() {
        storage.clear();
    }

    @Test
    void clear() {
        int expectedResumes = 0;
        storage.clear();
        Assertions.assertEquals(expectedResumes, storage.getSize());
    }

    @Test
    void update() {
        Resume resume3 = new Resume("u3");
        Resume resume3BeforeUpdate = storage.getAll()[1];

        storage.update(resume3);
        Assertions.assertAll(
                () -> assertTrue(storage.get("u3") == resume3),
                () -> assertFalse(storage.get("u3") == resume3BeforeUpdate)
        );

        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    Resume resume2 = new Resume("u2");
                    storage.update(resume2);
                }
        );
    }

    @Test
    void save() {
        Resume r10 = new Resume("u10");
        storage.save(r10);
        Assertions.assertAll("Сheck by element and quantity",
                () -> assertNotNull(storage.get("u10")),
                () -> assertEquals(6, storage.getSize())
        );
        Assertions.assertThrows(ExistStorageException.class,
                () -> {
                    storage.save(r10);
                }
        );
    }

    @Test
    void saveOverFlow() {
        storage.clear();
        int maxValue = AbstractArrayStorage.STORAGE_CAPACITY;
        try {
            for (int i = 0; i < maxValue; i++) {
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
        Resume r7 = new Resume("u7");
        Assertions.assertAll("Сheck by real uuid and fake uuid",
                () -> assertEquals(storage.get("u7"), r7),
                () -> assertNull(storage.get("u10"))
        );
    }

    @Test
    void delete() {
        storage.delete("u3");
        checkOrder();
        Assertions.assertThrows(NotExistStorageException.class,
                () -> {
                    storage.delete("u111");
                }
        );
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        int expectedLength = 5;
        Assertions.assertAll("Check every element",
                () -> assertEquals(new Resume("u1"), resumes[0]),
                () -> assertEquals(new Resume("u3"), resumes[1]),
                () -> assertEquals(new Resume("u5"), resumes[2]),
                () -> assertEquals(new Resume("u7"), resumes[3]),
                () -> assertEquals(new Resume("u9"), resumes[4]),
                () -> assertEquals(expectedLength, storage.getSize())
        );
    }

    @Test
    void getSize() {
        int expectedLength = 5;
        assertEquals(expectedLength, storage.getSize());
    }

    @Deprecated
    protected abstract void checkOrder();
}