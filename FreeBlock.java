// -------------------------------------------------------------------------
/**
 * Represents a FreeBlock that has the beginning index to be used in
 * freeBlocklist
 * 
 * @author asifrahman
 * @version Apr 28, 2024
 */
public class FreeBlock {

    private int beginningIndex;

    // ----------------------------------------------------------
    /**
     * Create a new FreeBlock object.
     * 
     * @param start
     *            beginning index of freeblock
     */
    public FreeBlock(int start) {
        this.beginningIndex = start;
    }


    // ----------------------------------------------------------
    /**
     * gets beginning index
     * 
     * @return startIndex
     */
    public int getStart() {
        return this.beginningIndex;
    }

}
