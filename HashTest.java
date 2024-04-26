
import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author asifrahman
 *  @version Apr 24, 2024
 */
public class HashTest extends TestCase {
    private Hash t2;

    public void setUp() {
        t2 = new Hash(10);
    }

    
    public void testInsert() {
        String[] arr = {"Fire", "far"};
        Record r1 = new Record(1, new Seminar(1, "r1", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r2 = new Record(35, new Seminar(35, "r2", "1111120229", 22, (short)3, (short)10, 30, arr, "something"));
        Record dupeOfr2 = new Record(35, new Seminar(35, "dupeOfr2", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r4 = new Record(28, new Seminar(28, "r4", "1011120229", 20, (short)2, (short)9, 30, arr, "some"));
        Record r5 = new Record(47, new Seminar(47, "r5", "1011120229", 28, (short)2, (short)9, 30, arr, "something"));
        Record r6 = new Record(63, new Seminar(63, "r6", "1011120229", 20, (short)2, (short)9, 80, arr, "something"));
        Record r7 = new Record(0, new Seminar(0, "r7", "1011120229", 20, (short)2, (short)9, 30, arr, "thing"));
        Record r100 = new Record(100, new Seminar(100, "r100", "1011120229", 20, (short)2, (short)9, 30, arr, "thing"));

        t2.insert(r1);
        t2.insert(r2);
        t2.insert(dupeOfr2);
        t2.insert(r4);
        t2.insert(r5);
        t2.insert(r6);
        t2.insert(r7);
        t2.print();
        assertEquals(20, t2.capacity());
        assertEquals(6, t2.size());
        t2.insert(r100);
        //t2.insert(new Record(55, new Handle(3, 2)));
        //t2.print();
        
    }


    public void testSearch() {
        String[] arr = {"Fire", "far", "chicken"};
        Record r1 = new Record(1, new Seminar(1, "r1", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r2 = new Record(35, new Seminar(35, "r2", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r4 = new Record(28, new Seminar(28, "r4", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r5 = new Record(47, new Seminar(47, "r5", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));

        t2.insert(r1);
        t2.insert(r2);
        t2.insert(r4);

        assertEquals(r1, t2.search(1));
        assertNull(t2.search(3));
        t2.delete(28);
        assertNull(t2.search(28));
        //t2.print();
    }
//
    public void testDelete() {
        String[] arr = {"Fire", "far", "chicken", "nine"};
        Record r1 = new Record(1, new Seminar(1, "r1", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r2 = new Record(35, new Seminar(35, "r2", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r4 = new Record(28, new Seminar(28, "r4", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));
        Record r5 = new Record(47, new Seminar(47, "r5", "1011120229", 20, (short)2, (short)9, 30, arr, "something"));

        t2.insert(r1);
        t2.insert(r2);
        t2.insert(r4);
        assertEquals(3, t2.size());
        
        t2.delete(1);
        t2.print();
        assertEquals(2, t2.size());
        t2.delete(35);
        t2.delete(3);
        
    }
    
}
