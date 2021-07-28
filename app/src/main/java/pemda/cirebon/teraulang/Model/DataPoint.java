package pemda.cirebon.teraulang.Model;

public class DataPoint {
    String Bulan;
    int Count;

    public DataPoint(){
    }

    public DataPoint(String bulan, int count) {
        Bulan = bulan;
        Count = count;
    }

    public String getBulan() {
        return Bulan;
    }

    public void setBulan(String bulan) {
        Bulan = bulan;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
