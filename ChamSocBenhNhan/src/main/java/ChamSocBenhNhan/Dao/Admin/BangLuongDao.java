package ChamSocBenhNhan.Dao.Admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import ChamSocBenhNhan.Dao.User.Compare;
import ChamSocBenhNhan.Entity.Admin.BangLuong;
import ChamSocBenhNhan.Entity.Admin.BangLuongMapper;
import ChamSocBenhNhan.Entity.Admin.CheckDKDVOfBangLuongMapper;
import ChamSocBenhNhan.Entity.Admin.CheckDangKyDichVuOfBangLuong;
import ChamSocBenhNhan.Entity.Admin.CheckIdExistDKDV;
import ChamSocBenhNhan.Entity.Admin.ChonMaNVMapper;
import ChamSocBenhNhan.Entity.Admin.ChonNhanVien;
import ChamSocBenhNhan.Entity.Admin.ChonTenNhanVienMapper;
import ChamSocBenhNhan.Entity.Admin.ListMaDKDVMapper;
import ChamSocBenhNhan.Entity.Admin.ListtMaDKDV;
import ChamSocBenhNhan.Entity.Admin.checkEmCorrectWithIdServiceWorkingMapper;
import ChamSocBenhNhan.Entity.Admin.chonThangNam;
import ChamSocBenhNhan.Entity.User.ListDangKyDichVu;

@Repository
public class BangLuongDao extends BaseDao {
	Date newDate2 = new Date();
	java.sql.Date newDateSql2 = new java.sql.Date(newDate2.getTime());
	Integer yearnow = Integer.parseInt(newDateSql2.toString().substring(0, newDateSql2.toString().indexOf("-")));
	Integer monthnow = Integer.parseInt(newDateSql2.toString().substring(newDateSql2.toString().indexOf("-") + 1,
			newDateSql2.toString().lastIndexOf("-")));
	Integer month = monthnow;
	Integer year = yearnow;
	String Sqlnoi = " ";

	public void locThongKe(chonThangNam ctn) {
		Integer month = ctn.getGiaTriThang();
		Integer year = ctn.getGiaTriNam();
		this.month = month;
		this.year = year;

		if (ctn.getMaHSNV() != null) {
			Sqlnoi = "and Month(NgayBatDau)=" + this.month + " and Year(ngayBatDau)=" + this.year
					+ " and bangluong.MaHSNV=" + ctn.getMaHSNV() + "";
		}

	}

	public void XemTatCa() {

		Sqlnoi = " ";

	}

	public int ThanhToanTatCa(Integer ttthang, Integer ttNam, Integer ttHSNV) {

		String sql = "update bangluong,dangkydichvu set bangluong.tinhTrangThanhToan='xong' where dangkydichvu.maDKDV=bangluong.maDKDV AND Year(dangkydichvu.ngayBatDau)="
				+ ttNam + " AND Month(dangkydichvu.ngayBatDau)=" + ttthang + " AND bangluong.MaHSNV=" + ttHSNV + "";
		return _jdbcTemplate.update(sql);
	}

	public List<BangLuong> getViewQlBangLuong() {
		return _jdbcTemplate.query(
				"select bangluong.idBangLuong, bangluong.maHSNV, hosonhanvien.hoTen, bangluong.maDKDV,dangkydichvu.maDichVu, bangluong.tinhTrangThanhToan,"
						+ "IF(luongTheothang!=0,DATEDIFF(ngayKetThuc,ngayBatDau)/30*luongTheoThang + ROUND(luongTheoThang/30,0)* DATEDIFF(ngayKetThuc,ngayBatDau)%30 ,"
						+ "0+IF(luongTheoNgay!=0,luongTheoNgay* DATEDIFF(ngayKetThuc,ngayBatDau),"
						+ "0+(IF(luongTheoGio!=0,IF(MINUTE(TIMEDIFF(gioKetThuc,gioBatDau))>30,(Hour(TIMEDIFF(gioKetThuc,gioBatDau))+1)*LuongTheoGio,Hour(TIMEDIFF(gioKetThuc,gioBatDau))*LuongTheoGio + ( dichvu.luongTheoGio/60)*MINUTE(TIMEDIFF(gioKetThuc,gioBatDau)) ),0)))) "
						+ "as tongThanhTien"
						+ " from bangluong,hosonhanvien,dangkydichvu,dichvu where dichvu.maDichVu=dangkydichvu.maDichVu and bangluong.maDKDV=dangkydichvu.maDKDV and bangluong.maHSNV=hosonhanvien.maHSNV "
						+ Sqlnoi + "",
				new BangLuongMapper());
	}

	public Map<ListtMaDKDV, ListtMaDKDV> getChonMaDKDV() {

		String sqlMadkdv = "SELECT maDKDV FROM dangkydichvu ";
		List<ListtMaDKDV> madkdv = _jdbcTemplate.query(sqlMadkdv, new ListMaDKDVMapper());
		String sqlMadkdvInBL = "SELECT maDKDV FROM bangluong ";
		List<ListtMaDKDV> madkdvInBL = _jdbcTemplate.query(sqlMadkdvInBL, new ListMaDKDVMapper());
		int check = 0;
		for (int i = 0; i < madkdvInBL.size(); i++) {
			for (int j = 0; j < madkdv.size(); j++) {
				if (madkdv.get(j).toString().equals(madkdvInBL.get(i).toString())) {
					check++;
				}
				if (check > 0) {
					madkdv.remove(j);
					check = 0;
				}
			}

		}

		Map<ListtMaDKDV, ListtMaDKDV> phones = new HashMap<ListtMaDKDV, ListtMaDKDV>();
		for (int i = 0; i < madkdv.size(); i++) {
			phones.put(madkdv.get(i), madkdv.get(i));
		}
		System.out.println(phones);
		return phones;
	}

	public Map<ChonNhanVien, ChonNhanVien> getChonMaNhanVien() {
		List<ChonNhanVien> listTen = new ArrayList<ChonNhanVien>();
		List<ChonNhanVien> listMa = new ArrayList<ChonNhanVien>();
		String sql2 = "SELECT maHSNV FROM hosonhanvien where tinhTrangDuyetHoSo='xong'";
		listMa = _jdbcTemplate.query(sql2, new ChonMaNVMapper());
		String sql = "SELECT hoTen FROM hosonhanvien where tinhTrangDuyetHoSo='xong' ";
		listTen = _jdbcTemplate.query(sql, new ChonTenNhanVienMapper());
		Map<ChonNhanVien, ChonNhanVien> phones = new HashMap<ChonNhanVien, ChonNhanVien>();
		for (int i = 0; i < listTen.size(); i++) {
			phones.put(listMa.get(i), listTen.get(i));
		}
		return phones;
	}

	public Map<ChonNhanVien, ChonNhanVien> getChonMaNhanVienTrongQLBLuong() {
		List<ChonNhanVien> listMa = new ArrayList<ChonNhanVien>();
		String sql2 = "SELECT maHSNV FROM hosonhanvien where tinhTrangDuyetHoSo='xong'";
		listMa = _jdbcTemplate.query(sql2, new ChonMaNVMapper());
		Map<ChonNhanVien, ChonNhanVien> phones = new HashMap<ChonNhanVien, ChonNhanVien>();
		for (int i = 0; i < listMa.size(); i++) {
			phones.put(listMa.get(i), listMa.get(i));
		}
		return phones;
	}

	public Map<Integer, String> getChonThang() {

		Map<Integer, String> states = new HashMap<Integer, String>();
		for (int i = 1; i < 13; i++) {
			states.put(i, "Th??ng " + i + "");
		}

		return states;
	}

	public Map<Integer, String> getChonNam() {

		Map<Integer, String> states = new HashMap<Integer, String>();
		for (int i = 2021; i < 2030; i++) {
			states.put(i, "N??m" + i + "");
		}

		return states;
	}

	public int luuThemQlBangLuong(BangLuong bl, ChonNhanVien cnv) {

		String sqlCheckEmCorrectWithIdServiceWorking = "SELECT hosonhanvien.hoTen, hosonhanvien.maHSNV,dichvu.maDichVu, dangkydichvu.maDichVu, hosonhanvien.maDichVu FROM `dangkydichvu`,dichvu,hosonhanvien WHERE dangkydichvu.maDichVu=dichvu.maDichVu and hosonhanvien.maDichVu=dichvu.maDichVu and dangkydichvu.maDKDV="
				+ bl.getMaDKDV() + " and hosonhanvien.maHSNV = " + cnv.getMaHSNV()
				+ " and hosonhanvien.tinhTrangDuyetHoSo='xong'";
		List<BangLuong> list_sqlCheckEmCorrectWithIdServiceWorking = _jdbcTemplate
				.query(sqlCheckEmCorrectWithIdServiceWorking, new checkEmCorrectWithIdServiceWorkingMapper());
		//
		if (!list_sqlCheckEmCorrectWithIdServiceWorking.isEmpty()) {

			String sqlCheck = "SELECT bangluong.maHSNV,hoTen,maDichVu,idBangLuong,bangluong.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien where hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.maHSNV = "
					+ cnv.getMaHSNV() + "";
			List<BangLuong> listE_InBL = _jdbcTemplate.query(sqlCheck, new BangLuongMapper());

			String sqlCheckIdExistDKDV = "SELECT maDKDV from dangkydichvu where maDKDV = " + bl.getMaDKDV() + "";
			List<ListDangKyDichVu> CheckIdExistDKDV = _jdbcTemplate.query(sqlCheckIdExistDKDV, new CheckIdExistDKDV());

			String sqlCheckIdDKDVInBL = "SELECT bangluong.maHSNV,hoTen,maDichVu,idBangLuong,bangluong.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien where hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.maDKDV = "
					+ bl.getMaDKDV() + " and bangluong.maHSNV=" + cnv.getMaHSNV() + "";
			List<BangLuong> listCheckIdDKDVInBL = _jdbcTemplate.query(sqlCheckIdDKDVInBL, new BangLuongMapper());

			// check t???n t???i nh??n vi??n ???? ??c ph??n c??ng trong b???ng l????ng, n???u ch??a t???n t???i
			// th??m th???ng

			if (listE_InBL.isEmpty()) {
				if (!CheckIdExistDKDV.isEmpty()) {
					System.out.println("222222222222222222");
					String sql = "insert into bangluong (maHSNV,maDKDV,tinhTrangThanhToan)values('" + cnv.getMaHSNV()
							+ "','" + bl.getMaDKDV() + "','chua')";
					// String sql_updateEm = "UPDATE `hosonhanvien` SET `tinhTrangLamViec`='ban'
					// WHERE maHSNV = "+cnv.getMaHSNV()+"";
					// sau khi anh insert v??o th?? anh ph???i c???p nh???t l???i t??nh tr???ng l??m vi???c c???a hsnv
					// l?? ch??a
					// _jdbcTemplate.update(sql_updateEm);
					return _jdbcTemplate.update(sql);

				}
				return 10;
			}
			if (!CheckIdExistDKDV.isEmpty() && listCheckIdDKDVInBL.isEmpty()) {
				// check nh??n vi??n kh??ng b???n l??m l???ch v???
				// - check ????ng k?? d???ch v??? chu???n b??? th??m v??o c?? ng??y th??ng n??m h???p l??
				// so v???i ng??y th??ng n??m ???? ????ng k?? d???ch v??? trong b???ng l????ng c???a nh??n vi??n ????
				// kh??ng
				String sqlCheckDateOfDKDV = "SELECT ngayBatDau, ngayKetThuc FROM dangkydichvu where dangkydichvu.maDKDV = "
						+ bl.getMaDKDV() + "";
				List<CheckDangKyDichVuOfBangLuong> listsqlCheckDateOfDKDV = _jdbcTemplate.query(sqlCheckDateOfDKDV,
						new CheckDKDVOfBangLuongMapper());

				String ngayBatDauMoi = listsqlCheckDateOfDKDV.get(0).toString().substring(0,
						listsqlCheckDateOfDKDV.get(0).toString().indexOf(","));
				String ngayKetThucMoi = listsqlCheckDateOfDKDV.get(0).toString().substring(
						listsqlCheckDateOfDKDV.get(0).toString().indexOf(",") + 1,
						listsqlCheckDateOfDKDV.get(0).toString().length());

				System.out.println(listsqlCheckDateOfDKDV.get(0));
				System.out.println(ngayBatDauMoi);
				System.out.println(ngayKetThucMoi);
				System.out.println("========");

				String sqlCheckDateOfDKDVinBLuong = "SELECT ngayBatDau, ngayKetThuc FROM dangkydichvu,hosonhanvien,bangluong where bangluong.maDKDV = dangkydichvu.maDKDV and  bangluong.maHSNV = hosonhanvien.maHSNV and bangluong.maHSNV= "
						+ cnv.getMaHSNV() + "";
				List<CheckDangKyDichVuOfBangLuong> listsqlCheckDateOfDKDVinBLuong = _jdbcTemplate
						.query(sqlCheckDateOfDKDVinBLuong, new CheckDKDVOfBangLuongMapper());

				String maxDateEndOld = listsqlCheckDateOfDKDVinBLuong.get(0).toString().substring(
						listsqlCheckDateOfDKDVinBLuong.get(0).toString().indexOf(",") + 1,
						listsqlCheckDateOfDKDVinBLuong.get(0).toString().length());

				String ngayBatDauCu = null;
				String ngayKetThucCu = null;

				int checkFalse = 0;
				for (int i = 0; i < listsqlCheckDateOfDKDVinBLuong.size(); i++) {
					System.out.println(listsqlCheckDateOfDKDVinBLuong.get(i));
					ngayBatDauCu = listsqlCheckDateOfDKDVinBLuong.get(i).toString().substring(0,
							listsqlCheckDateOfDKDVinBLuong.get(i).toString().indexOf(","));
					ngayKetThucCu = listsqlCheckDateOfDKDVinBLuong.get(i).toString().substring(
							listsqlCheckDateOfDKDVinBLuong.get(i).toString().indexOf(",") + 1,
							listsqlCheckDateOfDKDVinBLuong.get(i).toString().length());

					System.out.println(ngayBatDauCu);
					System.out.println(ngayKetThucCu);
					if ((Compare.Contanin1DatesIn2Date(ngayBatDauMoi, ngayBatDauCu, ngayKetThucCu))
							|| (Compare.Contanin1DatesIn2Date(ngayKetThucMoi, ngayBatDauCu, ngayKetThucCu)))

					{
						checkFalse++;
					} else {
						System.out.println("trwwwwwwwwwwwwwwww");
					}
				}
				// k s??? d???ng
				for (CheckDangKyDichVuOfBangLuong e : listsqlCheckDateOfDKDVinBLuong) {
					ngayKetThucCu = e.toString().substring(e.toString().indexOf(",") + 1, e.toString().length());
					if (Compare.checkDateThan(ngayKetThucCu, maxDateEndOld)) {
						maxDateEndOld = ngayKetThucCu;
					}
				}
				// ????ng k s??? d???ng

				if (checkFalse == 0) {
					String sql = "insert into bangluong (maHSNV,maDKDV,tinhTrangThanhToan)values('" + cnv.getMaHSNV()
							+ "','" + bl.getMaDKDV() + "','chua')";
					// sau khi anh insert v??o th?? anh ph???i c???p nh???t l???i t??nh tr???ng l??m vi???c c???a hsnv
					// l?? ch??a
					return _jdbcTemplate.update(sql);
				} else {
					return 0;
				}

			}
			return 10;
		}
		return 11;

	}

	// ????ng b???ng l????ng
	
	public BangLuong getSuaBangLuong(int idBangLuong) {
		String sql = "SELECT hosonhanvien.maHSNV,hoTen,dangkydichvu.maDichVu,idBangLuong,dangkydichvu.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien,dangkydichvu where dangkydichvu.maDKDV = bangluong.maDKDV and hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.idBangLuong =?";
		return _jdbcTemplate.queryForObject(sql, new Object[] { idBangLuong },
				new BeanPropertyRowMapper<BangLuong>(BangLuong.class));

	}

	public Map<String, String> getChonTinhTrangThanhToan() {

		Map<String, String> states = new HashMap<String, String>();
		states.put("chua", "ch??a");
		states.put("xong", "???? thanh to??n");

		return states;
	}

	public int luuSuaBangLuong(BangLuong p) {
		String sqlGetBlCu = "SELECT bangluong.maHSNV,hoTen,dangkydichvu.maDichVu,idBangLuong,bangluong.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien,dangkydichvu where dangkydichvu.maDKDV = bangluong.maDKDV and hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.idBangLuong ="
				+ p.getIdBangLuong() + "";
		List<BangLuong> listGetIdBlcu = _jdbcTemplate.query(sqlGetBlCu, new BangLuongMapper());
		String maHSNVcu = listGetIdBlcu.get(0).toString().substring(0, listGetIdBlcu.get(0).toString().indexOf(","));
		String maDKDVcu = listGetIdBlcu.get(0).toString().substring(listGetIdBlcu.get(0).toString().indexOf(",") + 1,
				listGetIdBlcu.get(0).toString().length());

		if (maHSNVcu.equals(p.getMaHSNV().toString()) && maDKDVcu.equals(p.getMaDKDV().toString())) {
			String sql = "update bangluong set maHSNV='" + p.getMaHSNV() + "', maDKDV='" + p.getMaDKDV()
					+ "',tinhTrangThanhToan='" + p.getTinhTrangThanhToan() + "' where idBangLuong=" + p.getIdBangLuong()
					+ "";
			return _jdbcTemplate.update(sql);
		} else

		{

			String sqlCheckEmCorrectWithIdServiceWorking = "SELECT hosonhanvien.hoTen, hosonhanvien.maHSNV,dichvu.maDichVu, dangkydichvu.maDichVu, hosonhanvien.maDichVu FROM `dangkydichvu`,dichvu,hosonhanvien WHERE dangkydichvu.maDichVu=dichvu.maDichVu and hosonhanvien.maDichVu=dichvu.maDichVu and dangkydichvu.maDKDV="
					+ p.getMaDKDV() + " and hosonhanvien.maHSNV = " + p.getMaHSNV()
					+ " and hosonhanvien.tinhTrangDuyetHoSo='xong'";
			List<BangLuong> list_sqlCheckEmCorrectWithIdServiceWorking = _jdbcTemplate
					.query(sqlCheckEmCorrectWithIdServiceWorking, new checkEmCorrectWithIdServiceWorkingMapper());
			//
			if (!list_sqlCheckEmCorrectWithIdServiceWorking.isEmpty()) {

				String sqlCheck = "SELECT bangluong.maHSNV,hoTen,maDichVu,idBangLuong,bangluong.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien where hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.maHSNV = "
						+ p.getMaHSNV() + "";
				List<BangLuong> listE_InBL = _jdbcTemplate.query(sqlCheck, new BangLuongMapper());

				String sqlCheckIdExistDKDV = "SELECT maDKDV from dangkydichvu where maDKDV = " + p.getMaDKDV() + "";
				List<ListDangKyDichVu> CheckIdExistDKDV = _jdbcTemplate.query(sqlCheckIdExistDKDV,
						new CheckIdExistDKDV());

				String sqlCheckIdDKDVInBL = "SELECT bangluong.maHSNV,hoTen,maDichVu,idBangLuong,bangluong.maDKDV,bangluong.tinhTrangThanhToan, 5 as tongThanhTien FROM bangluong,hosonhanvien where hosonhanvien.maHSNV = bangluong.maHSNV and bangluong.maDKDV = "
						+ p.getMaDKDV() + " and bangluong.maHSNV=" + p.getMaHSNV() + " and bangluong.maDKDV!="
						+ maDKDVcu + "";
				List<BangLuong> listCheckIdDKDVOfEInBL = _jdbcTemplate.query(sqlCheckIdDKDVInBL, new BangLuongMapper());

				// check t???n t???i nh??n vi??n ???? ??c ph??n c??ng trong b???ng l????ng, n???u ch??a t???n t???i
				// th??m th???ng

				if (listE_InBL.isEmpty()) {
					if (!CheckIdExistDKDV.isEmpty()) {
						String sql = "update bangluong set maHSNV='" + p.getMaHSNV() + "', maDKDV='" + p.getMaDKDV()
								+ "',tinhTrangThanhToan='" + p.getTinhTrangThanhToan() + "' where idBangLuong="
								+ p.getIdBangLuong() + "";
						return _jdbcTemplate.update(sql);

					}
					return 10;
				}
				if (!CheckIdExistDKDV.isEmpty() && listCheckIdDKDVOfEInBL.isEmpty()) {

					String sqlCheckDateOfDKDV = "SELECT ngayBatDau, ngayKetThuc FROM dangkydichvu where dangkydichvu.maDKDV = "
							+ p.getMaDKDV() + "";
					List<CheckDangKyDichVuOfBangLuong> listsqlCheckDateOfDKDV = _jdbcTemplate.query(sqlCheckDateOfDKDV,
							new CheckDKDVOfBangLuongMapper());

					String ngayBatDauMoi = listsqlCheckDateOfDKDV.get(0).toString().substring(0,
							listsqlCheckDateOfDKDV.get(0).toString().indexOf(","));
					String ngayKetThucMoi = listsqlCheckDateOfDKDV.get(0).toString().substring(
							listsqlCheckDateOfDKDV.get(0).toString().indexOf(",") + 1,
							listsqlCheckDateOfDKDV.get(0).toString().length());

					String sqlCheckDateOfDKDVinBLuong = "SELECT ngayBatDau, ngayKetThuc FROM dangkydichvu,hosonhanvien,bangluong where bangluong.maDKDV = dangkydichvu.maDKDV and  bangluong.maHSNV = hosonhanvien.maHSNV and bangluong.maHSNV= "
							+ p.getMaHSNV() + "";
					List<CheckDangKyDichVuOfBangLuong> listsqlCheckDateOfDKDVinBLuong = _jdbcTemplate
							.query(sqlCheckDateOfDKDVinBLuong, new CheckDKDVOfBangLuongMapper());

					String ngayBatDauCu = null;
					String ngayKetThucCu = null;

					int checkFalse = 0;
					for (int i = 0; i < listsqlCheckDateOfDKDVinBLuong.size(); i++) {
						System.out.println(listsqlCheckDateOfDKDVinBLuong.get(i));
						ngayBatDauCu = listsqlCheckDateOfDKDVinBLuong.get(i).toString().substring(0,
								listsqlCheckDateOfDKDVinBLuong.get(i).toString().indexOf(","));
						ngayKetThucCu = listsqlCheckDateOfDKDVinBLuong.get(i).toString().substring(
								listsqlCheckDateOfDKDVinBLuong.get(i).toString().indexOf(",") + 1,
								listsqlCheckDateOfDKDVinBLuong.get(i).toString().length());

						if ((Compare.Contanin1DatesIn2Date(ngayBatDauMoi, ngayBatDauCu, ngayKetThucCu))
								|| (Compare.Contanin1DatesIn2Date(ngayKetThucMoi, ngayBatDauCu, ngayKetThucCu))

						) {
							checkFalse++;
						} else {
							System.out.println("trwwwwwwwwwwwwwwww");
						}
					}

					if (checkFalse == 0) {
						String sql = "update bangluong set maHSNV='" + p.getMaHSNV() + "', maDKDV='" + p.getMaDKDV()
								+ "',tinhTrangThanhToan='" + p.getTinhTrangThanhToan() + "' where idBangLuong="
								+ p.getIdBangLuong() + "";
						return _jdbcTemplate.update(sql);
					} else {
						return 0;
					}

				}
				return 10;
			}
			return 11;

		}
	}

	public int thanhToanBangLuong(int idBangLuong) {
		String sql = "update bangluong set tinhTrangThanhToan='xong' where idBangLuong=" + idBangLuong + "";
		return _jdbcTemplate.update(sql);
	}

	public int xoaBangLuong(int id) {
		String sql = "delete from bangluong where idBangLuong=" + id + "";
		return _jdbcTemplate.update(sql);
	}

}
