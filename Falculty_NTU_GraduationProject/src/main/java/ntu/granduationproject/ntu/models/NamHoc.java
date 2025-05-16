package ntu.granduationproject.ntu.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "namhoc")
public class NamHoc {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int manamhoc;
	private int tennamhoc;
	public int getManamhoc() {
		return manamhoc;
	}
	public void setManamhoc(int manamhoc) {
		this.manamhoc = manamhoc;
	}
	public int getTennamhoc() {
		return tennamhoc;
	}
	public void setTennamhoc(int tennamhoc) {
		this.tennamhoc = tennamhoc;
	}
	
	
	
}
