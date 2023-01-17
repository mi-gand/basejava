package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Deprecated
public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Deprecated
    @Override
    protected void checkOrder() {
        Resume[] resumes = storage.getAllinArr();
        int expectedLength = 4;
        Assertions.assertAll("Deleted u3 in abstractClass",
                () -> assertEquals(new Resume("u1"), resumes[0]),
                () -> assertEquals(new Resume("u5"), resumes[1]),
                () -> assertEquals(new Resume("u7"), resumes[2]),
                () -> assertEquals(new Resume("u9"), resumes[3]),
                () -> assertEquals(expectedLength, storage.getSize())
        );
    }
}
