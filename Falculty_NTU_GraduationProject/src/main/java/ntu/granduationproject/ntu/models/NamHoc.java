package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "namhoc")
public class NamHoc {
	@Id
	private int manamhoc;
	private String tennamhoc;
	public int getManamhoc() {
		return manamhoc;
	}
	public void setManamhoc(int manamhoc) {
		this.manamhoc = manamhoc;
	}
	public String getTennamhoc() {
		return tennamhoc;
	}
	public void setTennamhoc(String tennamhoc) {
		this.tennamhoc = tennamhoc;
	}
	
}
