package pemda.cirebon.teraulang.Model;

public class DataPoint {
    int Bulan;
    int Count;

    public DataPoint(){
    }

    public DataPoint(int bulan, int count) {
        Bulan = bulan;
        Count = count;
    }

    public int getBulan() {
        return Bulan;
    }

    public void setBulan(int bulan) {
        Bulan = bulan;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
