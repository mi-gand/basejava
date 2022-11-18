/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            } else {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        if (r.uuid == null) {         //если пользователь вбивает только команду save без uuid
            return;
        }
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        try {
            for (int i = 0; i < storage.length; i++) {
                 if (storage[i].toString().equals(uuid)) {
                    return storage[i];
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Такого uuid нет");
        }
        return null;
    }

    void delete(String uuid) {
        int deletedIndex = -1;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].toString().equals(uuid)) {
                deletedIndex = i;
                shiftArray(deletedIndex);
                break;
            }
        }
    }

    private void shiftArray(int startIndex) {       //сдвиг массива на место удаляемого элемента
        int lastResumeIndex;
        for (int i = startIndex; i < storage.length; i++) {
            if ((storage[i] == null)) {
                lastResumeIndex = i - 1;
                for (int j = startIndex; j <= lastResumeIndex; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    Resume[] getAll() {
        int newArraySize = size();
        Resume[] response = new Resume[newArraySize];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
            response[i] = storage[i];
        }
        return response;
    }

    int size() {
        int counter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }
}
