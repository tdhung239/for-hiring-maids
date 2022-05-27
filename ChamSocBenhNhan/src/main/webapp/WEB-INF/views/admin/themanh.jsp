<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ol class="breadcrumb mb-4">
</ol>
${param.message}
<div class="outside"
	style="width: 80%; margin: 0 auto; padding-top: 50px; padding-bottom: 50px; min-height: 200px; background: #F5F5F5; border-radius: 10px 10px; box-shadow: 0 6px 12px 0 rgb(0 0 0/ 20%);">

	<form:form method="post" enctype="multipart/form-data"
		action="/ChamSocBenhNhan/luuThemAnh">
		<table
			style="margin: 0 auto; padding: 20px; padding-left: 130px; border-collapse: separate; border-spacing: 40px 20px;">
			<tr>
				<th colspan="2">
					<h3
						style="color: red; font-family: Arial; text-align: center; margin-bottom: 20px; font-weight: bold;">THÊM
						MỚI DỮ LIỆU </h3>
				</th>
			</tr>
			<tr>
				<td style="font-size: 18px;">Nhập tên công ty :&nbsp &nbsp</td>
				<td><form:input size="58" path="tenCongTy" required="required" /></td>
			</tr>
			
			<tr>
				<td style="font-size: 18px;">Chọn hình hình ảnh dịch vụ 1 :&nbsp
					&nbsp</td>
				<td style=""><input required="required" type="file"
					name="profile" /></td>
			</tr>
			<tr>
				<td style="font-size: 18px;">Chọn hình hình ảnh dịch vụ 2 :&nbsp
					&nbsp</td>
				<td style=""><input required="required" type="file"
					name="profile2" /></td>
			</tr>
		
			

			<tr>

				<th colspan="2"> <input
					style="margin-top: 40px; font-weight: bold; padding: 10px; color: red; border-radius: 10px 10px; font-size: 20px; background: linear-gradient(90deg, #FFEFD5, #FFEBCD); border: white; box-shadow: 0 6px 12px 0 rgb(0 0 0/ 20%);"
					type="submit" value="Thêm mới" /></th>
			</tr>
		</table>
	</form:form>
</div>
