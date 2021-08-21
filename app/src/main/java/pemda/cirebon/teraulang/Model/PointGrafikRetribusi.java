package pemda.cirebon.teraulang.Model;

public class PointGrafikRetribusi {

    String Bulan;
    int BiayaRetribusi;

    public PointGrafikRetribusi(){
    }

    public PointGrafikRetribusi(String bulan, int biayaRetribusi) {
        Bulan = bulan;
        BiayaRetribusi = biayaRetribusi;
    }

    public String getBulan() {
        return Bulan;
    }

    public void setBulan(String bulan) {
        Bulan = bulan;
    }

    public int getBiayaRetribusi() {
        return BiayaRetribusi;
    }

    public void setBiayaRetribusi(int biayaRetribusi) {
        BiayaRetribusi = biayaRetribusi;
    }
}
