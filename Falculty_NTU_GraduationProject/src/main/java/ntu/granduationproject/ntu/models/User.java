package ntu.granduationproject.ntu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "taikhoan")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long matk;
  private String tendn;
  private String matkhau;
public Long getMatk() {
	return matk;
}
public void setMatk(Long matk) {
	this.matk = matk;
}
public String getTendn() {
	return tendn;
}
public void setTendn(String tendn) {
	this.tendn = tendn;
}
public String getMatkhau() {
	return matkhau;
}
public void setMatkhau(String matkhau) {
	this.matkhau = matkhau;
}
  
  
  
}