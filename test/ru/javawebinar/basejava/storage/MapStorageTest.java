package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;

class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
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