package ntu.granduationproject.ntu.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "danhgiadetai")
public class DanhGiaDeTai {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "msdt",referencedColumnName = "msdt")
	private Project msdt;
	
	@ManyToOne
	@JoinColumn(name = "msgv",referencedColumnName = "msgv")
	private GiangVien msgv;
	
	@Column(precision = 4, scale = 2)
	private BigDecimal diem;
	
	@Column(columnDefinition = "TEXT")
	private String binhluan;

	

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

	public GiangVien getMsgv() {
		return msgv;
	}

	public void setMsgv(GiangVien msgv) {
		this.msgv = msgv;
	}

	public BigDecimal getDiem() {
		return diem;
	}

	public void setDiem(BigDecimal diem) {
		this.diem = diem;
	}

	public String getBinhluan() {
		return binhluan;
	}

	public void setBinhluan(String binhluan) {
		this.binhluan = binhluan;
	}
	
	
}
