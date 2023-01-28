package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int counter = 0;
        for (Map.Entry<String, Resume> line : storage.entrySet()) {
            counter++;
            if (line.getKey().equals(uuid)) {
                return counter;
            }
        }
        return null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int counter = 0;
        for (Map.Entry<String, Resume> line : storage.entrySet()) {
            counter++;
            if (counter == (Integer) searchKey) {
                return line.getValue();
            }
        }
        throw new RuntimeException("Something wrong");
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        int counter = 0;
        for (Map.Entry<String, Resume> line : storage.entrySet()) {
            counter++;
            if (counter == (Integer) searchKey) {
                storage.remove(line.getKey());
                return;
            }
        }
        throw new RuntimeException("Something wrong");
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
