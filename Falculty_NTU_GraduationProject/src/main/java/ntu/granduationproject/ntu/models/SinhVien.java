package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "sinhvien")
public class SinhVien {

  @Id
  private String mssv;


    private String matkhau;
    private String email;
    private String hoten;

    // Constructor mặc định
    public SinhVien() {}

    // Constructor với tham số
    public SinhVien(String mssv, String matkhau, String email, String hoten) {
        super();
        this.mssv = mssv;
        this.matkhau = matkhau;
        this.email = email;
        this.hoten = hoten;
        
    }

    // Getters và Setters
    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }


}
