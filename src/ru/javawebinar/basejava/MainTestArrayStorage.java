package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;

import static ru.javawebinar.basejava.MainArray.TEST_FULL_NAME;

/**
 * Test for your urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final AbstractArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", TEST_FULL_NAME);
        Resume r2 = new Resume("uuid2", TEST_FULL_NAME);
        Resume r3 = new Resume("uuid3", TEST_FULL_NAME);

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        Resume r3_1 = new Resume(r3.getUuid(), TEST_FULL_NAME);
        ARRAY_STORAGE.save(r3_1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        Resume r4 = new Resume("uuid9", TEST_FULL_NAME);
        ARRAY_STORAGE.update(r4);                       //выдает "такого резюме не существует"
        ARRAY_STORAGE.save(r4);
        System.out.println("Get r4 with uuid9: " + ARRAY_STORAGE.get(r4.getUuid()));

        Resume r5 = new Resume("uuid3", TEST_FULL_NAME);
        ARRAY_STORAGE.update(r5);
        Resume r6 = new Resume("uuid12", TEST_FULL_NAME);
        ARRAY_STORAGE.save(r6);
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.getSize());

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
