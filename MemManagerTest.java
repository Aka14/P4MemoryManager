
import student.TestCase;
public class MemManagerTest extends TestCase{
    private MemManager m1;
    
    public void setUp() {
        m1 = new MemManager(256);
        
    }
    
    public void testSize() {
        assertEquals(9, m1.sizeOfFBL());
    }
    
    public void testInsert() {
        byte[] arr = new byte[28];
        arr[0] = 0x28;
        arr[0] = 0x29;
        arr[0] = 0x30;
        Handle h1 = m1.insert(arr, 28);
        //assertEquals(228, m1.sizeOfCurrPool());
        assertEquals(28, m1.length(h1));
        byte[] arr2 = new byte[64];
        arr2[0] = 0x32;
        arr2[0] = 0x33;
        arr2[0] = 0x34;
        Handle h2 = m1.insert(arr2, 64);
        assertEquals(64, m1.length(h2));
        //assertEquals(164, m1.sizeOfCurrPool());
        byte[] arr3 = new byte[129];
        arr3[0] = 0x40;
        arr3[1] = 0x41;
        Handle h3 = m1.insert(arr3, 129);
        assertEquals(129, m1.length(h3));
        assertEquals(10, m1.sizeOfFBL());
        assertEquals(512, m1.getMemCapacity());
    }

}
