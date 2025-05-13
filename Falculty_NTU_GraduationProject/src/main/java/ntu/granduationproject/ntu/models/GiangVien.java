package ntu.granduationproject.ntu.models;

import jakarta.persistence.*;

@Entity
@Table(name = "giangvien")
public class GiangVien {
    @Id
    private String msgv;
    private String matkhau;
    private String email;
    private int HMHDDA ;
    private int HMHDCD ;
	private String hoten;
	@Column(name="isadmin")
	private boolean isAdmin;

	@Lob
	@Column(name = "CVNangLuc", columnDefinition = "LONGTEXT")
	private String cv;

    public GiangVien () {}


	public GiangVien(String msgv, String matkhau, String email, int hMHDDA, int hMHDCD, String sdt, String hoten) {
		super();
		this.msgv = msgv;
		this.matkhau = matkhau;
		this.email = email;
		HMHDDA = hMHDDA;
		HMHDCD = hMHDCD;
		this.hoten = hoten;
	}

	public int getHMHDDA() {
		return HMHDDA;
	}


	public void setHMHDDA(int hMHDDA) {
		HMHDDA = hMHDDA;
	}


	public int getHMHDCD() {
		return HMHDCD;
	}


	public void setHMHDCD(int hMHDCD) {
		HMHDCD = hMHDCD;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}
}
