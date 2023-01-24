package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, Integer index) {
        index = Math.abs(index) - 1;
        int copyElements = size - index;
        System.arraycopy(storage, index, storage, index + 1, copyElements);
        storage[index] = r;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, getSize(), new Resume(uuid), Resume::compareTo);
    }

    @Override
    protected void fillDeletedElement(Integer deletedIndex) {
        int copyElements = size - deletedIndex;
        System.arraycopy(storage, deletedIndex + 1, storage, deletedIndex, copyElements);
    }
}
