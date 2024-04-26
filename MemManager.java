import java.util.LinkedList;
import java.lang.Math;
// -------------------------------------------------------------------------
/**
 * MemManager:
 * 
 * @author asifrahman
 * @version Apr 15, 2024
 */
public class MemManager {

    private LinkedList<FreeBlock>[] freeBlockList; 
    private byte[] memPool;
    private int memCapacity;
    private int currMemSize;

    // ----------------------------------------------------------
    /**
     * Create a new MemManager object.
     * @param poolSize
     */
    @SuppressWarnings("unchecked")
    public MemManager(int poolSize) {
        currMemSize = 0;
        this.memCapacity = poolSize;
        this.memPool = new byte[poolSize];
        double output = Math.log(poolSize) / Math.log(2);
        int logMaxSize = (int) output;
        if(logMaxSize != output) logMaxSize += 1;
        this.freeBlockList = new LinkedList[logMaxSize+1];
        
        for(int i=0; i<freeBlockList.length; i++) {
            freeBlockList[i] = new LinkedList<FreeBlock>();
        }
        freeBlockList[freeBlockList.length-1].add(new FreeBlock(0));
        
       
    }
    
    public int sizeOfFBL() {
        return freeBlockList.length;
    }
    
    public int sizeOfCurrPool() {
        return memCapacity - currMemSize;
    }
    
    private int calcIndex(int size) {
        while(size >= 1) {
            size /= 2;
        }
        return size;
    }
    
    // Insert a record and return its position handle.

    // space contains the record to be inserted, of length size.


    public Handle insert(byte[] space, int size) {
        int freeBlockIndex = 0;
        FreeBlock block = null;
        // calculate list index of interest
        
        int ceiling = (int) Math.ceil((Math.log(size))/ Math.log(2));
        
        // starting at index of interest, find first available block
        
        for(int i = ceiling; i<freeBlockList.length; i++) {
            if(freeBlockList[i].size() != 0) {
                
                block = freeBlockList[i].get(0);
                freeBlockIndex = i;
                break;
                // store first available block and prob index 
            }
        }
        if(block == null) {
            
        }
        while(freeBlockIndex > ceiling && freeBlockList[freeBlockIndex].get(0)!= null) {
            freeBlockList[freeBlockIndex].remove(0);
            freeBlockIndex--;
            freeBlockList[freeBlockIndex].add(block);
            FreeBlock secondHalf = new FreeBlock((int)Math.pow(2.0, freeBlockIndex));
            freeBlockList[freeBlockIndex].add(secondHalf);
        }
        System.arraycopy(space, 0, memPool, freeBlockList[ceiling].get(0).getStart(), size);
        currMemSize += size;
        int startOfHandle = freeBlockList[ceiling].get(0).getStart();
        freeBlockList[ceiling].remove(0);
        Handle receipt = new Handle(startOfHandle, size);
        return receipt;
        // while index is too high, split available block
        // split available block into two
        // add second block to corresponding list
        // repeat process with first block
    }

    // Return the length of the record associated with theHandle


    public int length(Handle theHandle) {
        return theHandle.getRecLength();
    }

    // Free a block at the position specified by theHandle.

    // Merge adjacent free blocks.


    public void remove(Handle theHandle) {
        int some = theHandle.getLocation();
//        int freeBlockIndex = 0;
        FreeBlock block = null;
        // calculate list index of interest
        
        int ceiling = (int) Math.ceil((Math.log(theHandle.getRecLength()))/ Math.log(2));
        
        FreeBlock temp = new FreeBlock(some);
        freeBlockList[ceiling].add(temp);
        
        for(int i = ceiling; i<freeBlockList.length; i++) {
            if() {
                
                
                // store first available block and prob index 
            }
        }
        
        
        int sizeB = (int) Math.pow(2, ceiling);
    }
    
    private FreeBlock getBuddy(Handle theHandle) {
        int ceiling = (int) Math.ceil((Math.log(theHandle.getRecLength()))/ Math.log(2));
        int buddyLocation = theHandle.getLocation() ^ ceiling;
        for(int i=0; i<freeBlockList[ceiling].size(); i++) {
            if(f)
        }
    }
    
    private boolean checkBuddy(FreeBlock b1, FreeBlock b2) {
        int b1start = b1.getStart();
        int b1size = b1.getSize();
        int b2start = b2.getStart();
        int b2size = b2.getSize();
        return (b1start | b1size) == (b2start | b2size);
    }


    // Return the record with handle posHandle, up to size bytes, by // copying
    // it into space.

    // Return the number of bytes actually copied into space.


//    public int get(byte[] space, Handle theHandle, int size);
//
//    // Dump a printout of the freeblock list
//
//
//    public void dump();
    
}
