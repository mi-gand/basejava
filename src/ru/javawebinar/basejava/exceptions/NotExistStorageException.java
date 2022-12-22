package ru.javawebinar.basejava.exceptions;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid){
        super(String.format("Resume with id: \"%s\" does not exist", uuid),uuid);
    }
}
