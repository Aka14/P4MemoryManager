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
    private int size;

    @SuppressWarnings("unchecked")
    public MemManager(int poolSize) {
        this.size = poolSize;
        this.memPool = new byte[poolSize];
        double output = Math.log(poolSize) / Math.log(2);
        int logMaxSize = (int) output;
        if(logMaxSize != output) logMaxSize += 1;
        this.freeBlockList = new LinkedList[logMaxSize];
        
        for(int i=0; i<freeBlockList.length; i++) {
            freeBlockList[i] = new LinkedList<FreeBlock>();
        }
        freeBlockList[freeBlockList.length-1].add(new FreeBlock(0, poolSize));
        
       
    }
    
    public int sizeOfFBL() {
        return freeBlockList.length;
    }
    
    
    // Insert a record and return its position handle.

    // space contains the record to be inserted, of length size.


    public Handle insert(byte[] space, int size) {
        int freeBlockIndex = 0;
        // calculate list index of interest
        
        int ceiling = (int) Math.ceil((Math.log(size) / Math.log(2)));
        
        // starting at index of interest, find first available block
        
        for(int i = ceiling; i<freeBlockList.length; i++) {
            if(freeBlockList[i].size() != 0) {
                
                FreeBlock block = freeBlockList[i].get(0);
                freeBlockIndex = i;
                // store first available block and prob index 
            }
        }
        while(freeBlockIndex > ceiling) {
            
        }
        // while index is too high, split available block
        // split available block into two
        // add second block to corresponding list
        // repeat process with first block
        
    }
//
//    // Return the length of the record associated with theHandle
//
//
//    public int length(Handle theHandle);
//
//    // Free a block at the position specified by theHandle.
//
//    // Merge adjacent free blocks.
//
//
//    public void remove(Handle theHandle);
//
//    // Return the record with handle posHandle, up to size bytes, by // copying
//    // it into space.
//
//    // Return the number of bytes actually copied into space.
//
//
//    public int get(byte[] space, Handle theHandle, int size);
//
//    // Dump a printout of the freeblock list
//
//
//    public void dump();
    
}
