package ntu.granduationproject.ntu.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detai")
public class Project {
	@Id
	private String msdt;
	private String tendt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "giangvien",referencedColumnName = "msgv")
	private GiangVien giangVien;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matheloai",referencedColumnName = "matheloai")
	private TheLoai theLoai;
	private String mota;
	private String noidung;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "malinhvuc",referencedColumnName = "malinhvuc")
	private LinhVuc linhVuc;
	private int sosvtoida;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manamhoc",referencedColumnName = "manamhoc")
	private NamHoc namHoc;
	private int khoasv;
	private String trangthai;
	private boolean cosvthuchien;
	public String getMsdt() {
		return msdt;
	}
	public void setMsdt(String msdt) {
		this.msdt = msdt;
	}
	public String getTendt() {
		return tendt;
	}
	public void setTendt(String tendt) {
		this.tendt = tendt;
	}
	public GiangVien getGiangVien() {
		return giangVien;
	}
	public void setGiangVien(GiangVien giangVien) {
		this.giangVien = giangVien;
	}
	public TheLoai getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(TheLoai theLoai) {
		this.theLoai = theLoai;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}
	public String getNoidung() {
		return noidung;
	}
	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	public LinhVuc getLinhVuc() {
		return linhVuc;
	}
	public void setLinhVuc(LinhVuc linhVuc) {
		this.linhVuc = linhVuc;
	}
	public int getSosvtoida() {
		return sosvtoida;
	}
	public void setSosvtoida(int sosvtoida) {
		this.sosvtoida = sosvtoida;
	}
	public NamHoc getNamHoc() {
		return namHoc;
	}
	public void setNamHoc(NamHoc namHoc) {
		this.namHoc = namHoc;
	}
	public int getKhoasv() {
		return khoasv;
	}
	public void setKhoasv(int khoasv) {
		this.khoasv = khoasv;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public boolean isCosvthuchien() {
		return cosvthuchien;
	}
	public void setCosvthuchien(boolean cosvthuchien) {
		this.cosvthuchien = cosvthuchien;
	}
	
	
}
