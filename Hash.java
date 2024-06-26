
public class Hash {

    private int capacity;
    private int size;
    private Record[] hashTable;

    public Hash(int hashSize) {
        capacity = hashSize;
        hashTable = new Record[hashSize];
    }
    
    public int capacity() {
        return this.capacity;
    }
    
    public int size() {
        return this.size;
    }


    public int h1(int k) {
        return k % capacity;
    }


    public int h2(int k) {
        return (((k / capacity) % (capacity / 2)) * 2) + 1;
    }


    private void expandCapacity() {
        size = 0;
        Record[] newHashT = new Record[capacity * 2];
        Record[] temp = this.hashTable;
        this.hashTable = newHashT;
        this.capacity = this.hashTable.length;
        for (int i = 0; i < temp.length; i++) {
            if(temp[i] != null && temp[i].getId() != -1) {
                insert(temp[i]);
            }
        }
        System.out.println("Hash table expanded to "+this.capacity);
    }


    // ----------------------------------------------------------
    /**
     * Inserting into hashTable.
     * @param val
     * Record to be added to hashTable
     */
    public void insert(Record val) {
        int pos = h1(val.getId());
        int i = h2(val.getId());
        while(hashTable[pos] != null && hashTable[pos].getId() != -1) {
            pos = h1(pos + i);
        }
        hashTable[pos] = val;
        size++;
        if(size > capacity * 0.5) {
            expandCapacity();
        }
    }
    
    
    
    public Record search(int key) {
        int pos = h1(key);
        int i = h2(key);
        
        while(hashTable[pos] != null) {
            if(hashTable[pos].getId() == key) return hashTable[pos];
            pos = h1(pos + i);
        }
        return null;
    }


    public void delete(int key) {
        int pos = h1(key);
        int i = h2(key);
        Record tombS = new Record(-1, null);
        while(hashTable[pos] != null) {
            if(hashTable[pos].getId() == key) {
                hashTable[pos] = tombS;
                size--;
            }
            pos = h1(pos + i);
        }
//        if (hashTable[pos].getId() != -1) {
//            hashTable[pos] = tombS;
//            size--;
//            System.out.println("Record with ID "+key+" successfully deleted from the database");
//        }
    }


    public void print() {
        System.out.println("Hashtable:");
        for (int i = 0; i < capacity; i++) {
            if (hashTable[i] == null) {
                continue;
            }
            if (hashTable[i].getId() == -1) {
                System.out.println(i + ": " + "TOMBSTONE");
            }
            else {
                System.out.println(i + ": " + hashTable[i].getId());
                //count++;
            }
        }
        System.out.println("total records: "+size);
    }
}
