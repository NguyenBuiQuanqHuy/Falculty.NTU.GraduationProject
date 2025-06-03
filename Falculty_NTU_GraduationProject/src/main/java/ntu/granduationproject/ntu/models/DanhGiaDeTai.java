package ntu.granduationproject.ntu.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "danhgiadetai")
public class DanhGiaDeTai {
	@Id
	private int ID;
	
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
}
