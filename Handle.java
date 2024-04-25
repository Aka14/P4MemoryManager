public class Handle {
    
    private int location;
    private int recLength;
    
    public Handle(int loc, int length) {
        this.location = loc;
        this.recLength = length;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getRecLength() {
        return recLength;
    }

    public void setRecLength(int recLength) {
        this.recLength = recLength;
    }
    
    

}
