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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ChamSocBenhNhan.Entity.User.ChonDichVu;
import ChamSocBenhNhan.Entity.User.ListNhanVienvaDichVu;
import ChamSocBenhNhan.Service.admin.NhanVienIml;

@Controller
public class NhanVienController extends BaseController { 
	@Autowired 
	NhanVienIml nv = new NhanVienIml();
 
	// *
	@RequestMapping(value = { "/quan-li/nhan-vien/{maNhanVien}" }) 
	public ModelAndView getViewQLNhanvien(@PathVariable String maNhanVien) { 
		_mvShare.addObject("nhanvienn", nv.getViewQlNhanVien(maNhanVien)); 
		_mvShare.setViewName("admin/viewqlnhanvien"); 
		return _mvShare; 
	}

	@RequestMapping("/quan-li/nhan-vien/them-nhan-vien") 
	public String themNhanVien(Model m) { 
		// m.addAttribute("chonTinhTrangLamViec", nv.getChonTinhTrangLamViec());
		// m.addAttribute("chonTinhTrangDuyetHoSo", nv.getChonTinhTrangDuyetHoSo());
		m.addAttribute("chonDichVu", nv.getChonDichVu()); 
		m.addAttribute("command", new ListNhanVienvaDichVu()); 
		return "admin/themnhanvien"; 
	}

	@RequestMapping(value = "/luuThemNhanVien", method = RequestMethod.POST) 
	public String luuThemNhanVien(@RequestParam(value = "profile") CommonsMultipartFile file, HttpSession s,
			ChonDichVu dv, ListNhanVienvaDichVu hsnv) throws UnsupportedEncodingException { 

		if (nv.luuNhanVien(file, s, dv, hsnv) ==1) { 
			System.out.println("tc");
			String message = "<script>alert('Ch??c m???ng b???n ???? th??m m???i nh??n vi??n th??nh c??ng!!!');</script>"; 

			return "redirect:/quan-li/nhan-vien/all?message=" + URLEncoder.encode(message, "UTF-8"); 
		}

		if (nv.luuNhanVien(file, s, dv, hsnv) ==5) { 
			System.out.println("tc");
			String message = "<script>alert('Th??m kh??ng th??nh c??ng, b???n ph???i nh???p s??? ??i???n tho???i t??? 10 s???!!!');</script>"; 

			return "redirect:/quan-li/nhan-vien/all?message=" + URLEncoder.encode(message, "UTF-8"); 
		}

		String message = "<script>alert('L??u kh??ng th??nh c??ng, c?? th??? l?? do b???n ch??a ????? 16 tu???i ho???c b???n ch??a ch???n ???nh!!!');</script>"; 

		return "redirect:/quan-li/nhan-vien/them-nhan-vien?message=" + URLEncoder.encode(message, "UTF-8"); 

	} 

	@RequestMapping("/quan-li/nhan-vien/suaNhanVien/{maHSNV}") 
	public String getSuaNhanVien(@PathVariable int maHSNV, Model m) { 
		m.addAttribute("chonDichVu", nv.getChonDichVu()); 
		m.addAttribute("chonTinhTrangLV", nv.getChonTinhTrangLamViec()); 
		m.addAttribute("command", nv.getSuaNhanVien(maHSNV)); 
		return "admin/suanhanvien"; 
	}

	@RequestMapping(value = "/luuSuaNhanVien", method = RequestMethod.POST) 
	public String luuSuaNhanVien(@RequestParam(value = "profile") CommonsMultipartFile file, HttpSession s, 
			ChonDichVu dv, @ModelAttribute("emp") ListNhanVienvaDichVu emp) throws UnsupportedEncodingException { 
		int kq = nv.luuSuaNhanvien(file, s, emp, dv); 

		if (kq == 11) { 
			 String message = "<script>alert('S???a kh??ng th??nh c??ng, t???n t???i nh??n vi??n trong b???ng l????ng, ????? tr??nh l???i ph??n c??ng b???n k n??n thay ?????i m?? dich v??? !!!');</script>";
			return "redirect:/quan-li/nhan-vien/suaNhanVien/" + emp.getMaHSNV() + "?message=" 
					+ URLEncoder.encode(message, "UTF-8"); 

		}

		else if (kq == 0) { 
			String message = "<script>alert('S???a kh??ng th??nh c??ng, n??m sinh ph???i l???n h??n 16 tu???i !!!');</script>"; 
			return "redirect:/quan-li/nhan-vien/suaNhanVien/" + emp.getMaHSNV() + "?message=" 
					+ URLEncoder.encode(message, "UTF-8"); 

		}
		
		else if (kq == 5) { 
			String message = "<script>alert('S???a kh??ng th??nh c??ng, nh???p s??? ??i???n tho???i ph???i t??? 10 s??? !!!');</script>"; 
			return "redirect:/quan-li/nhan-vien/suaNhanVien/" + emp.getMaHSNV() + "?message=" 
					+ URLEncoder.encode(message, "UTF-8"); 

		}
		else { 
			System.out.println("tc"); 
			String message = "<script>alert('C???p nh???t th??nh c??ng!!!');</script>";
			return "redirect:quan-li/nhan-vien/all?message=" + URLEncoder.encode(message, "UTF-8"); 
		} 
 
	}

	@RequestMapping(value = "quan-li/nhan-vien/xoaNhanVien/{maHSNV}", method = RequestMethod.GET) 
	public String xoaNhanVien(@PathVariable int maHSNV) throws UnsupportedEncodingException { 

		int kq = nv.xoaNhanVien(maHSNV); 
		if (kq > 0) { 
			String message = "<script>alert('X??a th??nh c??ng!!!');</script>"; 
			return "redirect:/quan-li/nhan-vien/all?message=" + URLEncoder.encode(message, "UTF-8"); 
		} else { 
			String message = "<script>alert('X??a kh??ng th??nh c??ng v?? ??ang c??n t???n t???i nh??n vi??n n??y b??n b???ng l????ng ch??a ???????c x??a!!!');</script>"; 
			return "redirect:/quan-li/nhan-vien/all?message=" + URLEncoder.encode(message, "UTF-8"); 

		} 

	} 
	// ????ng k?? t??m vi???c

	@RequestMapping(value = { "/quan-li/dangky-timviec" })
	public ModelAndView getViewQLDangKyTimViec(HttpSession ss) {
		ss.removeAttribute("tbdktv"); 
		_mvShare.addObject("dktv", nv.getViewQLDangKyTimViec());
		_mvShare.setViewName("admin/viewql_dangky_timviec");
		return _mvShare;
	}

	@RequestMapping(value = "/quan-li/duyetDKTV/{maHSNV}", method = RequestMethod.GET)
	public String duyetDKTV(@PathVariable int maHSNV) {
		nv.duyetDKTV(maHSNV);
		return "redirect:/quan-li/dangky-timviec";
	}

	@RequestMapping(value = "quan-li/xoaDKTV/{maHSNV}", method = RequestMethod.GET)
	public String xoaDKTV(@PathVariable int maHSNV) throws UnsupportedEncodingException {

		int kq = nv.xoaDKTV(maHSNV);
		if (kq > 0) {
			String message = "<script>alert('X??a th??nh c??ng!!!');</script>";
			return "redirect:/quan-li/dangky-timviec?message=" + URLEncoder.encode(message, "UTF-8");
		} else {
			String message = "<script>alert('X??a kh??ng th??nh c??ng v?? ??ang t???n t???i nh??n vi??n n??y c?? ??? trong b???ng l????ng!!!');</script>";
			return "redirect:/quan-li/dangky-timviec?message=" + URLEncoder.encode(message, "UTF-8");

		}

	}
}
