public class Record {
    
    private int id;
   // private Handle handle;
    private Seminar sem;
    
    public Record(int iD, Seminar s) {
        this.id = iD;
        this.sem = s;
    }

    public int getId() {
        return id;
    }

//    public Handle getHandle() {
//        return handle;
//    }
    
    public Seminar getSem() {
        return sem;
    }
    
    
}
