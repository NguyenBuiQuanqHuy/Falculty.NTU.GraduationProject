package ntu.granduationproject.ntu.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detai")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

	private int msdt;
	private String tendt;
	
	@ManyToOne
	@JoinColumn(name = "msgv",referencedColumnName = "msgv")
	private GiangVien msgv;
	
	@ManyToOne
	@JoinColumn(name = "theloai",referencedColumnName = "matheloai")
	private TheLoai theLoai;
	private String mota;
	
	 @Column(columnDefinition = "LONGTEXT")
	private String noidung;
	
	@ManyToOne
	@JoinColumn(name = "linhvuc",referencedColumnName = "malinhvuc")
	private LinhVuc linhVuc;
	private int sosvtoida;
	
	
	
	private int khoasv;
	
	
	@ManyToOne
	@JoinColumn(name = "namhoc",referencedColumnName = "manamhoc")
	private NamHoc namHoc;
	private String trangthai;
	private boolean cosvthuchien;
	
	
	
	public int getMsdt() {
		return msdt;
	}
	public void setMsdt(int msdt) {
		this.msdt = msdt;
	}
	public String getTendt() {
		return tendt;
	}
	public void setTendt(String tendt) {
		this.tendt = tendt;
	}
	
	
	
	public GiangVien getMsgv() {
		return msgv;
	}
	public void setMsgv(GiangVien msgv) {
		this.msgv = msgv;
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
	
    private int msdt;

    private String tendt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgv", referencedColumnName = "msgv")
    private GiangVien msgv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theloai", referencedColumnName = "matheloai")
    private TheLoai theLoai;

    private String mota;

    @Column(columnDefinition = "LONGTEXT")
    private String noidung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linhvuc", referencedColumnName = "malinhvuc")
    private LinhVuc linhVuc;

    private int sosvtoida;

    private int khoasv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "namhoc", referencedColumnName = "manamhoc")
    private NamHoc namHoc;

    private String trangthai;

    private boolean cosvthuchien;

    // Getters và Setters

    public int getMsdt() {
        return msdt;
    }

    public void setMsdt(int msdt) {
        this.msdt = msdt;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
    }

    public GiangVien getMsgv() {
        return msgv;
    }

    public void setMsgv(GiangVien msgv) {
        this.msgv = msgv;
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

    public int getKhoasv() {
        return khoasv;
    }

    public void setKhoasv(int khoasv) {
        this.khoasv = khoasv;
    }

    public NamHoc getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(NamHoc namHoc) {
        this.namHoc = namHoc;
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
