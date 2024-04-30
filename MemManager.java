import java.util.LinkedList;
import java.lang.Math;

// -------------------------------------------------------------------------
/**
 * MemManager: Contains memPool and freeBlockList and uses buddy method to
 * efficiently keep track of seminar objects
 * 
 * @author asifrahman
 * @version Apr 15, 2024
 */
public class MemManager {

    private LinkedList<FreeBlock>[] freeBlockList;
    private byte[] memPool;
    private int memCapacity;
// private int currMemSize;

    // ----------------------------------------------------------
    /**
     * Create a new MemManager object.
     * 
     * @param poolSize
     *            size of MemPool
     */
    @SuppressWarnings("unchecked")
    public MemManager(int poolSize) {
// currMemSize = 0;
        this.memCapacity = poolSize;
        this.memPool = new byte[poolSize];
        double output = Math.log(poolSize) / Math.log(2);
        int logMaxSize = (int)output;
        if (logMaxSize != output)
            logMaxSize += 1;
        this.freeBlockList = new LinkedList[logMaxSize + 1];

        for (int i = 0; i < freeBlockList.length; i++) {
            freeBlockList[i] = new LinkedList<FreeBlock>();
        }
        freeBlockList[freeBlockList.length - 1].add(new FreeBlock(0));

    }


    // ----------------------------------------------------------
    /**
     * Getter of FreeBlockList length
     * 
     * @return size of FBL
     */
    public int sizeOfFBL() {
        return freeBlockList.length;
    }


    // ----------------------------------------------------------
    /**
     * Gets memCapacity (poolSize)
     * 
     * @return memCapacity
     */
    public int getMemCapacity() {
        return memCapacity;
    }

// public int sizeOfCurrPool() {
// return memCapacity - currMemSize;
// }


    // ----------------------------------------------------------
    /**
     * Insert a record and return its position handle.
     *
     * space contains the record to be inserted, of length size.
     * 
     * @param space
     *            Info being placed into memPool
     * @param size
     *            how big the info is
     * @return handle that allows you to return back to the info
     */
    public Handle insert(byte[] space, int size) {
        int freeBlockIndex = 0;
        FreeBlock block = null;
        // calculate list index of interest

        int ceiling = (int)Math.ceil((Math.log(size)) / Math.log(2));

        // starting at index of interest, find first available block

        for (int i = ceiling; i < freeBlockList.length; i++) {
            if (freeBlockList[i].size() != 0) {

                block = freeBlockList[i].get(0);
                freeBlockIndex = i;
                break;
                // store first available block and prob index
            }
        }
        if (block == null) {
            expandMemManager();
            return insert(space, size);
        }
        // while index is too high, split available block
        while (freeBlockIndex > ceiling && freeBlockList[freeBlockIndex].get(
            0) != null) {
            freeBlockList[freeBlockIndex].remove(0);
            freeBlockIndex--;
            // split available block into two
            freeBlockList[freeBlockIndex].add(block);
            FreeBlock secondHalf = new FreeBlock((int)Math.pow(2.0,
                freeBlockIndex) + block.getStart());
            freeBlockList[freeBlockIndex].add(secondHalf); // add second block
                                                           // to corresponding
                                                           // list
        }
        System.arraycopy(space, 0, memPool, freeBlockList[ceiling].get(0)
            .getStart(), size);
// currMemSize += size;
        int startOfHandle = freeBlockList[ceiling].get(0).getStart();
        freeBlockList[ceiling].remove(0);
        Handle receipt = new Handle(startOfHandle, size);
        return receipt;
        // repeat process with first block
    }


    @SuppressWarnings("unchecked")
    private void expandMemManager() {
        // memCapacity *= 2;
        byte[] newMemPool = new byte[memCapacity * 2];
        System.arraycopy(this.memPool, 0, newMemPool, 0, memCapacity);
        this.memPool = newMemPool;
        memCapacity = memPool.length;
        LinkedList<FreeBlock>[] newFBL = new LinkedList[this.sizeOfFBL() + 1];
        newFBL[newFBL.length - 1] = new LinkedList<FreeBlock>();
        System.arraycopy(this.freeBlockList, 0, newFBL, 0,
            this.freeBlockList.length);
        this.freeBlockList = newFBL;
        freeBlockList[sizeOfFBL() - 2].add(new FreeBlock((int)Math.pow(2.0,
            sizeOfFBL() - 2)));
        System.out.println("Memory pool expanded to " + memCapacity + " bytes");
    }


    // ----------------------------------------------------------
    /**
     * Return the length of the record associated with theHandle
     * 
     * @param theHandle
     *            receipt that holds map back to info in memPool
     * @return true size of record
     */
    public int length(Handle theHandle) {
        return theHandle.getRecLength();
    }

    // Free a block at the position specified by theHandle.

    // Merge adjacent free blocks.


    public void remove(Handle theHandle) {
        // calculate list index of interest

        int ceiling = (int)Math.ceil((Math.log(theHandle.getRecLength())) / Math
            .log(2));


        checkBuddy(theHandle.getLocation(), ceiling);

    }


    private void checkBuddy(int startPos, int powerOf2) {
        if (freeBlockList[powerOf2].size() < 1) { // Case 1: When that blockSize index is not free at all
            freeBlockList[powerOf2].add(new FreeBlock(startPos)); // Insert at specific index
            return;
        }
        FreeBlock head = freeBlockList[powerOf2].get(0); 
        if (head.getStart() > startPos) { // Case 2: When the thing to remove starts before the head
            boolean checkBud = calcBuddy(startPos, head.getStart(), (int)Math //checks if it is buddy with head
                .pow(2, powerOf2));
            if (checkBud) {
                freeBlockList[powerOf2].remove(0); // If true, then pop head and merge as greater block
                checkBuddy(startPos, powerOf2+1); //recursively check with next index
            }else {
                freeBlockList[powerOf2].add(0, new FreeBlock(startPos)); //else add as new head of list at powerOf2 index
            }
        }else { //Case 3: When it is greater start position than the head of the powerOf2 index list
            for(int k = 0; k<freeBlockList[powerOf2].size(); k++) {
                if(startPos < freeBlockList[powerOf2].get(k).getStart()) { // Checks if the current block of the for loop is buddy with the thing to remove
                    boolean checkBud = calcBuddy(startPos, head.getStart(), (int)Math
                        .pow(2, powerOf2));
                    if (checkBud) { //Repeat same merging as case 2
                        freeBlockList[powerOf2].remove(0);
                        checkBuddy(startPos, powerOf2+1);
                    }else {
                        freeBlockList[powerOf2].add(0, new FreeBlock(startPos));
                    }
                    break;
                }
            } 
            if(startPos > freeBlockList[powerOf2].getLast().getStart()) { //Adds to end of the list at the powerOf2 index of the FreeBlockList
                freeBlockList[powerOf2].add(new FreeBlock(startPos));
            }
        }
        

    }
    

    

    private boolean calcBuddy(int b1Start, int b2Start, int b1Size) {
        return (b1Start | b1Size) == (b2Start | b1Size);
    }


    private FreeBlock getBuddy(Handle theHandle) {
        int ceiling = (int)Math.ceil((Math.log(theHandle.getRecLength())) / Math
            .log(2));
        int blockSize = (int)Math.pow(2, ceiling);
        int buddyLocation = theHandle.getLocation() ^ blockSize;
        for (int i = 0; i < freeBlockList[ceiling].size(); i++) {
            if ((freeBlockList[ceiling].get(i).getStart()
                ^ blockSize) == buddyLocation) {
                return freeBlockList[ceiling].get(i);
            }
        }
        return null;
    }

// public void remove(Handle theHandle) {
// int some = theHandle.getLocation();
// int freeBlockIndex = 0;
// FreeBlock block = null;
// // calculate list index of interest
//
// int ceiling = (int) Math.ceil((Math.log(theHandle.getRecLength()))/
// Math.log(2));
//
// FreeBlock temp = new FreeBlock(some);
// freeBlockList[ceiling].add(temp);
//
// for(int i = ceiling; i<freeBlockList.length; i++) {
// if() {
//
//
// // store first available block and prob index
// }
// }
//
//
// int sizeB = (int) Math.pow(2, ceiling);
// }
//
// private FreeBlock getBuddy(Handle theHandle) {
// int ceiling = (int) Math.ceil((Math.log(theHandle.getRecLength()))/
// Math.log(2));
// int buddyLocation = theHandle.getLocation() ^ ceiling;
// for(int i=0; i<freeBlockList[ceiling].size(); i++) {
// if(f)
// }
// }
//
// private boolean checkBuddy(FreeBlock b1, FreeBlock b2) {
// int b1start = b1.getStart();
// int b1size = b1.getSize();
// int b2start = b2.getStart();
// int b2size = b2.getSize();
// return (b1start | b1size) == (b2start | b2size);
// }

    // Return the record with handle posHandle, up to size bytes, by // copying
    // it into space.

    // Return the number of bytes actually copied into space.


    public void get(byte[] space, Handle theHandle) {
        System.arraycopy(memPool, theHandle.getLocation(), space, 0, theHandle.getRecLength());
    }
//
// Dump a printout of the freeblock list


    // ----------------------------------------------------------
    /**
     * String representation of FreeBlockList
     */
    public void dump() {
        boolean notEmpty = false;
        System.out.println("Freeblock List:");
        for (int i = 0; i < freeBlockList.length; i++) {
            if (freeBlockList[i].size() != 0) {
                notEmpty = true;
                break;
            }
        }
        if (!notEmpty) {
            System.out.println("There are no freeblocks in the memory pool");
            return;
        }
        for (int j = 0; j < freeBlockList.length; j++) {
            if (freeBlockList[j].size() != 0) {
                System.out.print("" + (int)Math.pow(2, j) + ": ");
                for (int k = 0; k < freeBlockList[j].size(); k++) {
                    System.out.print("" + freeBlockList[j].get(k).getStart()
                        + " ");
                }
                System.out.println();
            }
        }

    }

}
