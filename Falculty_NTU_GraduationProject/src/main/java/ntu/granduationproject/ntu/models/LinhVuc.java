package ntu.granduationproject.ntu.models;

import org.hibernate.internal.util.StringHelper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "linhvuc")
public class LinhVuc {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int malinhvuc;
	private String tenlinhvuc;
	public int getMalinhvuc() {
		return malinhvuc;
	}
	public void setMalinhvuc(int malinhvuc) {
		this.malinhvuc = malinhvuc;
	}
	public String getTenlinhvuc() {
		return tenlinhvuc;
	}
	public void setTenlinhvuc(String tenlinhvuc) {
		this.tenlinhvuc = tenlinhvuc;
	}
	
}
