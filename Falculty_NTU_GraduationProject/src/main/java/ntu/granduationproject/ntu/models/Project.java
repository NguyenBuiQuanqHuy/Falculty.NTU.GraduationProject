package ntu.granduationproject.ntu.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "detai")
public class Project {
	@Id
	private String msdt;
	private String tendt;
	private String msgv;
	private int theloai;
	private String mota;
	private String noidung;
	private int linhvuc;
	private int sosvtoida;
	private int namhoc;
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
	public String getMsgv() {
		return msgv;
	}
	public void setMsgv(String msgv) {
		this.msgv = msgv;
	}
	public int getTheloai() {
		return theloai;
	}
	public void setTheloai(int theloai) {
		this.theloai = theloai;
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
	public int getLinhvuc() {
		return linhvuc;
	}
	public void setLinhvuc(int linhvuc) {
		this.linhvuc = linhvuc;
	}
	public int getSosvtoida() {
		return sosvtoida;
	}
	public void setSosvtoida(int sosvtoida) {
		this.sosvtoida = sosvtoida;
	}
	public int getNamhoc() {
		return namhoc;
	}
	public void setNamhoc(int namhoc) {
		this.namhoc = namhoc;
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
