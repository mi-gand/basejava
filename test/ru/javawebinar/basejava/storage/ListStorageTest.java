package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest extends AbstractStorageTest {

    protected ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    void testClear() {
        storage.clear();
        List<Resume> allResumes = storage.getAllinList();
        Assertions.assertAll("Check field \"size\" and array length",
                () -> assertSize(0),
                () -> assertEquals(0, allResumes.size())
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
    }

    @Test
    void testGetAllinList() {
        List<Resume> resumes = storage.getAllinList();
        List<Resume> expectedResumes = initResumeList();
        Assertions.assertTrue(resumes.containsAll(expectedResumes));
        Assertions.assertTrue(assertSize(5));
    }

    List<Resume> initResumeList(){
        return Arrays.asList(
                new Resume(UUID_1),
                new Resume(UUID_3),
                new Resume(UUID_5),
                new Resume(UUID_7),
                new Resume(UUID_9)
        );
    }
}