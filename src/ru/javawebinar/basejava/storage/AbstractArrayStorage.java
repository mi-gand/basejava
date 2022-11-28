package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_CAPACITY = 10000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Такого резюме не существует");
        } else {
            storage[index] = resume;
            System.out.println("Резюме с id: " + uuid + " обновлено");
        }
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (size == STORAGE_CAPACITY - 1) {
            System.out.println("Хранилище переполнено");
        } else if (index > 0) {
            System.out.println("Резюме с id: " + uuid + "уже существует");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме с id: " + uuid + " не существует");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int getSize() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}