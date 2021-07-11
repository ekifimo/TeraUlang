package pemda.cirebon.teraulang.Model;

public class UserLogin {
    String Nama;
    String NIP;
    String Password;

    public UserLogin(){

    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserLogin(String nama, String NIP, String password) {
        Nama = nama;
        this.NIP = NIP;
        Password = password;
    }
}

