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
        String tmpUuid = resume.getUuid();
        int resumeExistOnIndex = checkExistResume(tmpUuid);
        if (resumeExistOnIndex != -1) {
            storage[resumeExistOnIndex] = resume;
            System.out.println("Резюме с id: " + tmpUuid + " обновлено");
        } else {
            System.out.println("Такого резюме не существует");
        }
    }

    public void save(Resume r) {
        if(size == STORAGE_CAPACITY - 1) {
            System.out.println("Хранилище переполнено");
            return;
        }
        String tmpUuid = r.getUuid();
        if (tmpUuid == null) {         //если пользователь вбивает только команду save без uuid
            return;
        }
        int resumeExistOnIndex = checkExistResume(tmpUuid);
        if (resumeExistOnIndex == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Резюме с id: " + tmpUuid + "уже существует");
        }
    }

    public Resume get(String uuid) {
        int resumeExistOnIndex = checkExistResume(uuid);
        if (resumeExistOnIndex != -1) {
            return storage[resumeExistOnIndex];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int resumeExistOnIndex = checkExistResume(uuid);
        if(resumeExistOnIndex != -1){
            storage[resumeExistOnIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }else{
            System.out.println("Резюме с id: " + uuid + " не существует");
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

    private int checkExistResume(String uuid) {
        if(size == 0) return -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}