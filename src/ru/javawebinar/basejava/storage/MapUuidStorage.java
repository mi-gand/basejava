package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return uuid != null;
    }

    @Override
    protected String getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume doGet(Object uuid) {
        return storage.get(uuid.toString());
    }

    @Override
    protected void doSave(Resume resume, Object uuid) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object uuid) {
        storage.remove(uuid.toString());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
