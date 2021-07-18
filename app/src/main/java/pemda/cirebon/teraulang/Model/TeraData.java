package pemda.cirebon.teraulang.Model;

public class TeraData {

    String Alamat, AnakTimbangan, Biaya, JenisTimbangan, Kapasitas, Kecamatan, Kelurahan,
        Nama, NoHp, TanggalTeraUlangAwal, TanggalTeraUlangBerikutnya, TanggalDropdown, Quantity, PId;

    public TeraData(){

    }

    public TeraData(String alamat, String anakTimbangan, String biaya, String jenisTimbangan,
                    String kapasitas, String kecamatan, String kelurahan, String nama, String noHp,
                    String tanggalTeraUlangAwal, String tanggalTeraUlangBerikutnya, String TanggalDropdown, String Quantity, String PId) {
        Alamat = alamat;
        AnakTimbangan = anakTimbangan;
        Biaya = biaya;
        JenisTimbangan = jenisTimbangan;
        Kapasitas = kapasitas;
        Kecamatan = kecamatan;
        Kelurahan = kelurahan;
        Nama = nama;
        NoHp = noHp;
        TanggalTeraUlangAwal = tanggalTeraUlangAwal;
        TanggalTeraUlangBerikutnya = tanggalTeraUlangBerikutnya;
        TanggalDropdown = TanggalDropdown;
        Quantity = Quantity;
        PId = PId;
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

    public String getBiaya() {
        return Biaya;
    }

    public void setBiaya(String biaya) {
        Biaya = biaya;
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

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return Kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        Kelurahan = kelurahan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNoHp() {
        return NoHp;
    }

    public void setNoHp(String noHp) {
        NoHp = noHp;
    }

    public String getTanggalTeraUlangAwal() {
        return TanggalTeraUlangAwal;
    }

    public void setTanggalTeraUlangAwal(String tanggalTeraUlangAwal) {
        TanggalTeraUlangAwal = tanggalTeraUlangAwal;
    }

    public String getTanggalTeraUlangBerikutnya() {
        return TanggalTeraUlangBerikutnya;
    }

    public void setTanggalTeraUlangBerikutnya(String tanggalTeraUlangBerikutnya) {
        TanggalTeraUlangBerikutnya = tanggalTeraUlangBerikutnya;
    }

    public String getTanggalDropdown() {
        return TanggalDropdown;
    }

    public void setTanggalDropdown(String tanggalDropdown) {
        TanggalDropdown = tanggalDropdown;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }
}
