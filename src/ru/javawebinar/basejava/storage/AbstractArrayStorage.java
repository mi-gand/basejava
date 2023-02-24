package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException("The storage is full", resume.getUuid());
        } else {
            insertResume(resume, (Integer) index);
            size++;
        }
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) (index) >= 0;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public List<Resume> getAllSorted() {
        return size != 0 ? Arrays.asList(storage) : Collections.emptyList();
    }

    public int getSize() {
        return size;
    }

    protected abstract void insertResume(Resume resume, Integer index);

    /*метод для заполнения пустой ячейки storage при удалении резюме*/
    protected abstract void fillDeletedElement(Integer deletedIndex);
}