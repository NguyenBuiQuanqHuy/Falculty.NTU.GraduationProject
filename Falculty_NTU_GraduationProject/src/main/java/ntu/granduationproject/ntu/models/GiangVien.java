package ntu.granduationproject.ntu.models;

import jakarta.persistence.*;

@Entity
@Table(name = "giangvien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String msgv;
    private String matkhau;

	private String hoten;
	@Column(name="isadmin")
	private boolean isAdmin;

	public boolean isIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean admin) {
		isAdmin = admin;
	}

	public String getMsgv() {
		return msgv;
	}

	public void setMsgv(String msgv) {
		this.msgv = msgv;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	public String getHoten() { return hoten; }

	public void setHoten(String hoten) { this.hoten = hoten; }
}
