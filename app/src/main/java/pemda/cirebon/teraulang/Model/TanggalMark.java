package pemda.cirebon.teraulang.Model;

public class TanggalMark {
    long UnixTimestamp;
    String TanggalMonitoring;

    public TanggalMark(){

    }

    public TanggalMark(long unixTimestamp, String tanggalMonitoring) {
        UnixTimestamp = unixTimestamp;
        TanggalMonitoring = tanggalMonitoring;
    }

    public long getUnixTimestamp() {
        return UnixTimestamp;
    }

    public void setUnixTimestamp(long unixTimestamp) {
        UnixTimestamp = unixTimestamp;
    }

    public String getTanggalMonitoring() {
        return TanggalMonitoring;
    }

    public void setTanggalMonitoring(String tanggalMonitoring) {
        TanggalMonitoring = tanggalMonitoring;
    }
}
