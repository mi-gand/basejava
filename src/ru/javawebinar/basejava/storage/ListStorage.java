package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> listStorage;

    public ListStorage() {
        this.listStorage = new LinkedList<>();
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public void update(Resume resume) {
        for (Resume r : listStorage) {
            if(r.getUuid().equals(resume.getUuid())){
                r = resume;
                return;
            }
        }
        throw new NotExistStorageException(resume.getUuid());
    }

    @Override
    public void save(Resume r) {
        if(listStorage.contains(r)){
            throw new ExistStorageException(r.getUuid());
        }else{
            listStorage.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        for (Resume resume : listStorage) {
            if(resume.getUuid().equals(uuid)){
                return resume;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> itr = listStorage.listIterator();
        while(itr.hasNext()){
            if(itr.next().getUuid().equals(uuid)){
                itr.remove();
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public List<Resume> getAllinList() {
        return listStorage;
    }

    @Override
    public int getSize() {
        return listStorage.size();
    }
}
