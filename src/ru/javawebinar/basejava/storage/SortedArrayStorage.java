package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, getSize(), new Resume(uuid), Resume::compareTo);
    }


    @Override
    public void save(Resume r) {
        if(size == 0 || r.getUuid().compareTo(storage[size - 1].getUuid()) > 0){
            storage[size] = r;
            size++;
        }else{
            System.out.println("Ошибка присвоения uuid");
        }
    }

    @Override
    public void delete(String uuid) {
        int deletedIndex = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                deletedIndex = i;
                shiftArray(deletedIndex);
                size--;
                break;
            }
        }
        if(deletedIndex < 0){
            System.out.println("Резюме с id: " + uuid + " не существует");
        }
    }

    private void shiftArray(int startIndex) {       //сдвиг массива на место удаляемого элемента
        for (int i = startIndex; i < size; i++) {
                storage[i] = storage [i + 1];
        }
    }
}
