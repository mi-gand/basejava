package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

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
    protected void doUpdate(Resume resume, Object index) {
        listStorage.set((Integer)index, resume);
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer)index >= 0;
    }


    @Override
    protected Integer getSearchKey(String uuid) {
        for(int i = 0; i < listStorage.size(); i++){
            if(listStorage.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return -1;
    }


    @Override
    protected Resume doGet(Object index) {
        return listStorage.get((Integer)index);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        listStorage.add(resume);
    }

    @Override
    protected void doDelete(Object index) {
        listStorage.remove((int)index);
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
