package pemda.cirebon.teraulang.Model;

public class CalenderNotes {
    String Nama, TanggalMonitoring, TanggalTeraUlangBerikutnya;
    long UnixTimestamp;

    public CalenderNotes(){

    }

    public CalenderNotes(String nama, String tanggalMonitoring, String tanggalTeraUlangBerikutnya, long unixTimestamp) {
        Nama = nama;
        TanggalMonitoring = tanggalMonitoring;
        TanggalTeraUlangBerikutnya = tanggalTeraUlangBerikutnya;
        UnixTimestamp = unixTimestamp;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTanggalMonitoring() {
        return TanggalMonitoring;
    }

    public void setTanggalMonitoring(String tanggalMonitoring) {
        TanggalMonitoring = tanggalMonitoring;
    }

    public String getTanggalTeraUlangBerikutnya() {
        return TanggalTeraUlangBerikutnya;
    }

    public void setTanggalTeraUlangBerikutnya(String tanggalTeraUlangBerikutnya) {
        TanggalTeraUlangBerikutnya = tanggalTeraUlangBerikutnya;
    }

    public long getUnixTimestamp() {
        return UnixTimestamp;
    }

    public void setUnixTimestamp(long unixTimestamp) {
        UnixTimestamp = unixTimestamp;
    }
}
