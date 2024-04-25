
import student.TestCase;
public class MemManagerTest extends TestCase{
    private MemManager m1;
    
    public void setUp() {
        m1 = new MemManager(1024);
        
    }
    
    public void testSize() {
        assertEquals(10, m1.sizeOfFBL());
    }

}
