package ChamSocBenhNhan.Entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FileAnhMapper implements RowMapper<FileAnh> {

	public FileAnh mapRow(ResultSet rs, int rowNum) throws SQLException {
		FileAnh list = new FileAnh();
		list.setId(rs.getInt("id"));
		list.setTenCongTy(rs.getString("tenCongTy"));
		list.setTenAnhDichVu1(rs.getString("tenAnhDichVu1"));
		list.setTenAnhDichVu2(rs.getString("tenAnhDichVu2"));
		return list;
	}

}