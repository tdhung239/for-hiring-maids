package ChamSocBenhNhan.adminController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ChamSocBenhNhan.Entity.Admin.BangLuong;
import ChamSocBenhNhan.Entity.Admin.ChonNhanVien;
import ChamSocBenhNhan.Entity.Admin.chonThangNam;
import ChamSocBenhNhan.Entity.User.ListDangKyDichVu;
import ChamSocBenhNhan.Service.admin.BangLuongIml;

@Controller
public class BangluongController extends BaseController {
	@Autowired
	private BangLuongIml blIml = new BangLuongIml();

	@RequestMapping(value = { "/quan-li/bang-luong" })
	public ModelAndView QLBangLuong(HttpSession ss) {
		ss.removeAttribute("tbbl");
		_mvShare.addObject("getChonThang", blIml.getChonThang());
		_mvShare.addObject("getChonNam", blIml.getChonNam());
		_mvShare.addObject("bangluong", blIml.getViewQlBangLuong());
		_mvShare.addObject("chonMaNhanVienTrongBL", blIml.getChonMaNhanVienTrongQLBLuong());
		_mvShare.addObject("command", new chonThangNam());
		_mvShare.setViewName("admin/viewqlbangluong");
		return _mvShare;
	}

	@RequestMapping(value = "/locBangLuong", method = RequestMethod.POST)
	public String locThongKe(chonThangNam ctn, HttpSession s) {
		s.setAttribute("ttthang", ctn.getGiaTriThang());
		s.setAttribute("ttNam", ctn.getGiaTriNam());
		s.setAttribute("ttHSNV", ctn.getMaHSNV());
		blIml.locThongKe(ctn);

		return "redirect:/quan-li/bang-luong";
	}

	@RequestMapping(value = "/XemTatCa", method = RequestMethod.POST)
	public String XemTatCa() {
		blIml.XemTatCa();
		return "redirect:/quan-li/bang-luong";
	}

	@RequestMapping(value = "/ThanhToanTatCa", method = RequestMethod.POST)
	public String ThanhToanTatCa(HttpSession s) {
		Integer ttNam = (Integer) s.getAttribute("ttNam");
		Integer ttthang = (Integer) s.getAttribute("ttthang");
		Integer ttHSNV = (Integer) s.getAttribute("ttHSNV");
		blIml.ThanhToanTatCa(ttthang, ttNam, ttHSNV);

		return "redirect:/quan-li/bang-luong";
	}

	@RequestMapping("/quan-li/bang-luong/them-bang-luong")
	public String themBangLuog(Model m) {
		m.addAttribute("chonMaNhanVien", blIml.getChonMaNhanVien());
		m.addAttribute("chonMaDKDVNOTBL", blIml.getChonMaDKDV());
		m.addAttribute("command", new ListDangKyDichVu());
		return "admin/thembangluong";
	}

	@RequestMapping(value = "/luuThemBangLuong", method = RequestMethod.POST)
	public String luuThemMoiBangLuong(BangLuong bl, ChonNhanVien cnv) throws UnsupportedEncodingException {

		int kq = blIml.luuThemQlBangLuong(bl, cnv);
		if (kq == 0) {
			String message = "<script>alert('Th??m kh??ng th??nh c??ng, m???i b???n ch???n d???ch v??? c???a nh??n vi??n n??y kh??ng b???n l??m d???ch v??? kh??c !!!');</script>";
			return "redirect:/quan-li/bang-luong/them-bang-luong?message=" + URLEncoder.encode(message, "UTF-8");

		} else if (kq == 10) {
			String message = "<script>alert('Th??m kh??ng th??nh c??ng, m???i b???n ki???m tra nh??n vi??n kh??ng b???n l??m d???ch v??? kh??c !!!');</script>";

			return "redirect:/quan-li/bang-luong/them-bang-luong?message=" + URLEncoder.encode(message, "UTF-8");
		} else if (kq == 11) {
			String message = "<script>alert('Th??m kh??ng th??nh c??ng, m???i b???n ch???n l???i d???ch v??? ????ng m?? nh??n vi??n ??ang l??m !!!');</script>";

			return "redirect:/quan-li/bang-luong/them-bang-luong?message=" + URLEncoder.encode(message, "UTF-8");
		}
		String message = "<script>alert('Th??m th??nh c??ng!!!');</script>";
		return "redirect:/quan-li/bang-luong?message=" + URLEncoder.encode(message, "UTF-8");

	}

	@RequestMapping("/quan-li/bang-luong/edit/{idBangLuong}")
	public String getSuaBangLuog(@PathVariable int idBangLuong, Model m) {
		m.addAttribute("chonMaNhanVien", blIml.getChonMaNhanVien());
		//m.addAttribute("chonMaDKDVNOTBL", blIml.getChonMaDKDV());
		// m.addAttribute("add", new ListDangKyDichVu());
		m.addAttribute("chonTinhTrangThanhToan", blIml.getChonTinhTrangThanhToan());

		m.addAttribute("command", blIml.getSuaBangLuong(idBangLuong));
		return "admin/suabangluong";
	}

	@RequestMapping(value = "/luuSuaBangLuong", method = RequestMethod.POST)
	public String luuSuaBangLuong(@ModelAttribute("emp") BangLuong emp) throws UnsupportedEncodingException {
		int kq = blIml.luuSuaBangLuong(emp);
		if (kq == 0) {
			String message = "<script>alert('Th??m kh??ng th??nh c??ng, m???i b???n ch???n d???ch v??? c???a nh??n vi??n n??y kh??ng b???n l??m d???ch v??? kh??c !!!');</script>";
			return "redirect:/quan-li/bang-luong/edit/" + emp.getIdBangLuong() + "?message="
					+ URLEncoder.encode(message, "UTF-8");

		} else if (kq == 10) {
			String message = "<script>alert('C???p nh???t kh??ng th??nh c??ng, m???i b???n ki???m tra nh???p l???i m?? ????ng k?? d???ch v??? c???a kh??ch h??ng kh??ng tr??ng v???i d???ch v??? kh??c m?? nh??n vi??n n??y ??ang l??m ho???c kh??ng c?? t???n t???i d???ch v??? n??y !!!');</script>";

			return "redirect:/quan-li/bang-luong/edit/" + emp.getIdBangLuong() + "?message="
					+ URLEncoder.encode(message, "UTF-8");
		} else if (kq == 11) {
			String message = "<script>alert('C???p nh???t kh??ng th??nh c??ng, m???i b???n ch???n l???i d???ch v??? ????ng m?? nh??n vi??n ??ang l??m !!!');</script>";

			return "redirect:quan-li/bang-luong/edit/" + emp.getIdBangLuong() + "?message="
					+ URLEncoder.encode(message, "UTF-8");
		}
		String message = "<script>alert('C???p nh???t th??nh c??ng!!!');</script>";
		return "redirect:quan-li/bang-luong?message=" + URLEncoder.encode(message, "UTF-8");

	}

	@RequestMapping(value = "/quan-li/bang-luong/thanh-toan/{idBangLuong}", method = RequestMethod.GET)
	public String thanhToanBangLuong(@PathVariable int idBangLuong) {
		blIml.thanhToanBangLuong(idBangLuong);
		return "redirect:/quan-li/bang-luong";
	}

	@RequestMapping(value = "/quan-li/xoa-bang-luong/{idBangLuong}", method = RequestMethod.GET)
	public String xoaBangLuong(@PathVariable int idBangLuong) throws UnsupportedEncodingException {
		int kq = blIml.xoaBangLuong(idBangLuong);
		if (kq > 0) {
			String message = "<script>alert('X??a th??nh c??ng!!!');</script>";
			return "redirect:/quan-li/bang-luong?message=" + URLEncoder.encode(message, "UTF-8");
		}
		String message = "<script>alert('X??a  th??nh c??ng!!!');</script>";
		return "redirect:/quan-li/bang-luong?message=" + URLEncoder.encode(message, "UTF-8");
	}

}
