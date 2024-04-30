import java.io.IOException;

/**
 * Handle the Seminar database. This class processes commands by manipulating
 * the hash table.
 *
 * @author asifrahman
 * @version 4/15/2024
 */
public class SeminarDB {
    private MemManager myMemman; // Implement Memory Manager class in a separate
                                 // file
    private Hash myHashTable; // Implement Hash table class in a separate file

    /**
     * Create a new SeminarDB object.
     *
     * @param initMemSize
     *            Initial size for memory pool
     * @param initHashSize
     *            Initial size for hash tables
     * @throws IOException
     */
    public SeminarDB(int initMemSize, int initHashSize) throws IOException {
        myMemman = new MemManager(initMemSize);
        myHashTable = new Hash(initHashSize);
    }


    // ----------------------------------------------------------
    /**
     * Process insert command, which requires a lot of parsing!
     * 
     * @param sID
     *            ID value
     * @param stitle
     *            title
     * @param sdate
     *            date
     * @param slength
     *            length
     * @param sx
     *            x
     * @param sy
     *            y
     * @param scost
     *            cost
     * @param skeywords
     *            keywords
     * @param sdesc
     *            description
     * @throws Exception
     */
    public void insert(
        int sID,
        String stitle,
        String sdate,
        int slength,
        int sx,
        int sy,
        int scost,
        String[] skeywords,
        String sdesc)
        throws Exception {
        Seminar semInsert = new Seminar(sID, stitle, sdate, slength, (short)sx,
            (short)sy, scost, skeywords, sdesc);
        byte[] semArr = semInsert.serialize();
        if (myHashTable.search(sID) != null) {
            System.out.println(
                "Insert FAILED - There is already a record with ID " + sID);
            return;
        }
        Handle hMM = myMemman.insert(semArr, semArr.length);
        Record semRec = new Record(sID, hMM);
        myHashTable.insert(semRec);
        System.out.println("Successfully inserted record with ID " + sID);
        System.out.println(semInsert.toString());
        byte[] byteSize = semInsert.serialize();
        System.out.println("Size: " + byteSize.length);

    }


    // ----------------------------------------------------------
    /**
     * Delete the record with the given key
     * 
     * @param sID
     *            The key to find and remove
     * @throws IOException
     */
    public void delete(int sID) throws IOException {
        Record some = myHashTable.search(sID);
        if (some == null) {
            System.out.println("Delete FAILED -- There is no record with ID "
                + sID);
            return;
        }
        myMemman.remove(some.getHandle());
        myHashTable.delete(sID);
        System.out.println("Record with ID " + sID
            + " successfully deleted from the database");
    }


    // ----------------------------------------------------------
    /**
     * Find and return the record that matches the given key
     * 
     * @param sID
     *            The key to search for
     * @throws IOException
     * @throws Exception
     */
    public void search(int sID) throws IOException, Exception {
        Record rec = myHashTable.search(sID);
        if (rec == null) {
            System.out.println("Search FAILED -- There is no record with ID "
                + sID);
            return;
        }
        byte[] arr = new byte[rec.getHandle().getRecLength()];
        myMemman.get(arr, rec.getHandle());
        System.out.println("Found record with ID " + sID + ":");
        Seminar sem = Seminar.deserialize(arr);
        System.out.println(sem.toString());
    }


    // ----------------------------------------------------------
    /**
     * Print the hash table
     * 
     * @throws IOException
     */
    public void hashprint() throws IOException {
        myHashTable.print();
    }


    // ----------------------------------------------------------
    /**
     * Print the memory manager freeblock list
     */
    public void memmanprint() {
        myMemman.dump();
    }
}
