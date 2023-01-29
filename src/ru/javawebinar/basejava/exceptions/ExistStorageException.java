package ru.javawebinar.basejava.exceptions;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super(String.format("Resume with id: \"%s\" already exist", uuid), uuid);
    }
}
