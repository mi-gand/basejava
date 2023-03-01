package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;

class MapResumeStorageTest extends AbstractStorageTest {

    protected MapResumeStorageTest() {
        super(new MapResumeStorage());
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
    }
}