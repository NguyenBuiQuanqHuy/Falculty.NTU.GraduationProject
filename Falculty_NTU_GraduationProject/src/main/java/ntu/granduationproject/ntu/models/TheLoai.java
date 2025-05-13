package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "theloai")
public class TheLoai {
	@Id
	private int matheloai;
	private String tentheloai;
	
	public int getMatheloai() {
		return matheloai;
	}
	public void setMatheloai(int matheloai) {
		this.matheloai = matheloai;
	}
	public String getTentheloai() {
		return tentheloai;
	}
	public void setTentheloai(String tentheloai) {
		this.tentheloai = tentheloai;
	}
	
	
}
