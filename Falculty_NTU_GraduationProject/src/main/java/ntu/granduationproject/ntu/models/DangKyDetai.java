package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dangkydetai")
public class DangKyDetai {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "msdt",referencedColumnName = "msdt")
	private Project msdt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mssv",referencedColumnName = "mssv")
	private SinhVien mssv;
	
	private String trangthai;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getMsdt() {
		return msdt;
	}

	public void setMsdt(Project msdt) {
		this.msdt = msdt;
	}

	public SinhVien getMssv() {
		return mssv;
	}

	public void setMssv(SinhVien mssv) {
		this.mssv = mssv;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	
	
}
