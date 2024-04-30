// -------------------------------------------------------------------------
/**
 * Record: Holds Handle but for now holds Seminar object with an ID as key
 * 
 * @author asifrahman
 * @version Apr 28, 2024
 */
public class Record {

    private int id;
    private Handle handle;

    // ----------------------------------------------------------
    /**
     * Create a new Record object.
     * 
     * @param iD
     *            seminar id
     * @param s
     *            seminar object
     */
    public Record(int iD, Handle h) {
        this.id = iD;
        this.handle = h;
    }


    // ----------------------------------------------------------
    /**
     * gets ID
     * 
     * @return id of seminar
     */
    public int getId() {
        return id;
    }


    public Handle getHandle() {
        return handle;
    }


    // ----------------------------------------------------------
    /**
     * Gets seminar object
     * 
     * @return seminar object
     */
//    public Seminar getSem() {
//        return sem;
//    }

}
