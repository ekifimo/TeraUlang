package pemda.cirebon.teraulang.Model;

public class PointGrafikUttp {
    String JenisUTTP;
    int Count;

    public PointGrafikUttp(){

    }

    public PointGrafikUttp(String jenisUTTP, int count) {
        JenisUTTP = jenisUTTP;
        Count = count;
    }

    public String getJenisUTTP() {
        return JenisUTTP;
    }

    public void setJenisUTTP(String jenisUTTP) {
        JenisUTTP = jenisUTTP;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
