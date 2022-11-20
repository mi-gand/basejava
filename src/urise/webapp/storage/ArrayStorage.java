package urise.webapp.storage;

import urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_CAPACITY = 10000;
    private Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int size;

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
        if (uuid == null) {         //если пользователь вбивает только команду save без uuid
            return;
        }
        int index = getIndex(uuid);
        if (size == STORAGE_CAPACITY - 1) {
            System.out.println("Хранилище переполнено");
            return;
        } else if (index < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Резюме с id: " + uuid + "уже существует");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            return null;
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

    final int getIndex(String uuid) {
        if (size == 0) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}