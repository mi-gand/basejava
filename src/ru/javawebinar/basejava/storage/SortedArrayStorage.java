package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, getSize(), new Resume(uuid), Resume::compareTo);
    }

    @Override
    protected void fillArray(int deletedIndex) {
        for (int i = deletedIndex; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    @Override
    protected boolean checkUuid(Resume r) {     //новое резюме не должно быть старше по порядку, чем предыдущее
        return (size == 0 || r.getUuid().compareTo(storage[size - 1].getUuid()) > 0);
    }
}
