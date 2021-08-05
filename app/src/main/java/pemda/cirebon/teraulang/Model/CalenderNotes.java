package pemda.cirebon.teraulang.Model;

public class CalenderNotes {
    String Nama, TanggalMonitoring, TanggalTeraUlangBerikutnya, Alamat, AnakTimbangan, JenisTimbangan, Kapasitas, Quantity, NoHp;
    long UnixTimestamp;

    public CalenderNotes(){

    }

    public CalenderNotes(String nama, String tanggalMonitoring, String tanggalTeraUlangBerikutnya, String alamat, String anakTimbangan,
                         String jenisTimbangan, String kapasitas, String quantity, String noHp, long unixTimestamp) {
        Nama = nama;
        TanggalMonitoring = tanggalMonitoring;
        TanggalTeraUlangBerikutnya = tanggalTeraUlangBerikutnya;
        Alamat = alamat;
        AnakTimbangan = anakTimbangan;
        JenisTimbangan = jenisTimbangan;
        Kapasitas = kapasitas;
        Quantity = quantity;
        NoHp = noHp;
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

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getAnakTimbangan() {
        return AnakTimbangan;
    }

    public void setAnakTimbangan(String anakTimbangan) {
        AnakTimbangan = anakTimbangan;
    }

    public String getJenisTimbangan() {
        return JenisTimbangan;
    }

    public void setJenisTimbangan(String jenisTimbangan) {
        JenisTimbangan = jenisTimbangan;
    }

    public String getKapasitas() {
        return Kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        Kapasitas = kapasitas;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getNoHp() {
        return NoHp;
    }

    public void setNoHp(String noHp) {
        NoHp = noHp;
    }

    public long getUnixTimestamp() {
        return UnixTimestamp;
    }

    public void setUnixTimestamp(long unixTimestamp) {
        UnixTimestamp = unixTimestamp;
    }
}
