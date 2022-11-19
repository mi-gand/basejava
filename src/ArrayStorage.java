import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        this.size = 0;
    }

    public void save(Resume r) {
        if (r.uuid == null) {         //если пользователь вбивает только команду save без uuid
            return;
        }
        storage[size] = r;
        this.size++;
    }

    public Resume get(String uuid) {
        Resume r = new Resume();
        for (int i = 0; i < this.size; i++) {
            if (storage[i].toString().equals(uuid)) {
                r = storage[i];
            }
        }
        return r;
    }

    public void delete(String uuid) {
        int deletedIndex = -1;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].toString().equals(uuid)) {
                deletedIndex = i;
                int lastResumeIndex = size - 1;
                storage[deletedIndex] = storage[lastResumeIndex];
                storage[lastResumeIndex] = null;
                break;
            }
        }
        this.size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOf(this.storage, this.size);
    }

    public int getSize() {
        return this.size;
    }
}