package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Deprecated
    default Resume[] getAllinArr(){     //для ListStorage и MapStorage
        return null;
    }

    default List<Resume> getAllinList(){     //для ArrayStorage и SortedArrayStorage
        return null;
    }

    int getSize();

}
