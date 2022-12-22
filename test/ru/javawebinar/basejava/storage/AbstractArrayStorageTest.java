package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest() {
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

    @Test
    void clear() {
        int expectedResumes = 0;
        storage.clear();
        Assertions.assertEquals(expectedResumes, storage.getSize());
    }

    @Test
    void update() {

    }

    @Test
    void save() {
        Resume r10 = new Resume("u10");
        storage.save(r10);
        Assertions.assertAll("Сheck by element and quantity",
                ()-> assertNotNull(storage.get("u10")),
                ()-> assertEquals(6,storage.getSize())
        );
        Assertions.assertThrows(ExistStorageException.class,
                ()-> {storage.save(r10);}
        );
    }

    @Test
    void saveOverFlow(){
        int maxValue = AbstractArrayStorage.STORAGE_CAPACITY;
        Assertions.assertThrows(StorageException.class,
                ()->{
                        for(int i = 1; i <= maxValue; i++){
                            Resume r = new Resume("u" + i);
                            storage.save(r);
                        }
                    });
    }

    @Test
    void get() {
        String fakeUuid = "u10";
        Resume r7 = new Resume("u7");
        Assertions.assertAll("Сheck by real uuid and fake uuid",
                ()-> assertEquals(storage.get("u7"), r7),
                ()-> assertNull(storage.get("u10"))
        );
    }

    @Test
    void delete() {
        storage.delete("u3");
        checkOrder();
        Assertions.assertThrows(NotExistStorageException.class,
                ()-> {storage.delete("u111");}
        );
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        int expectedLength = 5;
        Assertions.assertAll("Check every element",
                ()->assertEquals(new Resume("u1"), resumes[0]),
                ()->assertEquals(new Resume("u2"), resumes[1]),
                ()->assertEquals(new Resume("u3"), resumes[2]),
                ()->assertEquals(new Resume("u4"), resumes[3]),
                ()->assertEquals(new Resume("u5"), resumes[4]),
                ()->assertEquals(expectedLength, storage.getSize())
        );
    }

    @Test
    void getSize() {
        int expectedLength = 5;
        assertEquals(expectedLength, storage.getSize());
    }

    protected abstract void checkOrder();
}