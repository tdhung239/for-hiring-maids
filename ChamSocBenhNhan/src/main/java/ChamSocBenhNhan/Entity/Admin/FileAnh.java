package ChamSocBenhNhan.Entity.Admin;

public class FileAnh {
	private Integer id;
	private String tenCongTy;
	private String tenAnhDichVu1;
	private String tenAnhDichVu2;

	public FileAnh() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenCongTy() {
		return tenCongTy;
	}

	public void setTenCongTy(String tenCongTy) {
		this.tenCongTy = tenCongTy;
	}

	public String getTenAnhDichVu1() {
		return tenAnhDichVu1;
	}

	public void setTenAnhDichVu1(String tenAnhDichVu1) {
		this.tenAnhDichVu1 = tenAnhDichVu1;
	}

	public String getTenAnhDichVu2() {
		return tenAnhDichVu2;
	}

	public void setTenAnhDichVu2(String tenAnhDichVu2) {
		this.tenAnhDichVu2 = tenAnhDichVu2;
	}
	@Override
	public String toString() {
		return id.toString();
	}
	
}
