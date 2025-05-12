package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sinhvien")
public class SinhVien {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String mssv;
  private String matkhau;
  private String email;
  private String hoten;

  private String sdt;

	public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

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

	public String getHoten() { return hoten; }

	public void setHoten(String hoten) { this.hoten = hoten; }

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
}