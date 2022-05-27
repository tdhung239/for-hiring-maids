<%@ page pageEncoding="UTF-8" language="java"
	contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>

<!-- Basic Page Needs
================================================== -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Công Ty Nghe Nhạc</title>

<!-- Mobile Specific Metas
================================================== -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Construction Html5 Template">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=5.0">

<!-- Favicon
================================================== -->


<link rel="icon" type="image/png"
	href="<c:url value="/assets/user/images/logogv.jpg"/> ">

<!-- CSS
================================================== -->
<!-- Bootstrap -->
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/bootstrap/bootstrap.min.css"/>">
<!-- FontAwesome -->
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/fontawesome/css/all.min.css"/>">
<!-- Animation -->
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/animate-css/animate.css"/>">
<!-- slick Carousel -->
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/slick/slick.css"/>">
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/slick/slick-theme.css"/>">
<!-- Colorbox -->
<link rel="stylesheet"
	href="<c:url value="/assets/user/plugins/colorbox/colorbox.css"/>">
<!-- Template styles-->
<link rel="stylesheet"
	href="<c:url value="/assets/user/css/style.css"/>">
<link rel="stylesheet" href="<c:url value="/assets/admin/taoanh.css"/>">
<link
	href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'>

</head>
<body>
	<c:forEach var="item" items="${ viewAnh }">
	<div class="dainhac">
							<img
							src="<c:url value="/assets/user/images/MusicRythm.png"/>"
							style="width: 1240px; height: 400px; fz-index: 500; margin-left:404px; margin-top:90px;  position: absolute;">
							</div>
				
		<div class="khung"
			style=" padding-top: 150px; background: url(<c:url value="/assets/user/images/Background.png"/>">
			
				<div class="khung-trong" style="width: 415px;">
					<div class="khung-trai">
						<img
							src="<c:url value="/assets/user/images/${item.tenAnhDichVu1}"/>"
							style="width: 230px; height: 230px; float: left; z-index: 500; border-radius: 50%; margin-left: 190px; position: absolute;">
					</div>
					<div class="khung-phai">
						<img
							src="<c:url value="/assets/user/images/${item.tenAnhDichVu2}"/>"
							style="width: 230px; height: 230px; z-index: 600; border-radius: 50%; position: absolute;">
					</div>
					<img
							src="<c:url value="/assets/user/images/icons8-next-page-50.png"/>"
							style=" z-index: 700;  position: absolute; margin-left: 198px; width: 30px; height: 30px; margin-top: 100px;">
				</div>
				<div class="tenCongTy" style="z-index: 100;margin-top: 180px;
				 "> <img
							src="<c:url value="/assets/user/images/icons8-play-60.png"/>"
							style="z-index: 600; width:30px; height:30px;  position: absolute; margin-top: 10px; margin-left: 477px;">
					<p style="padding: 6px 6px; font-family:Arial;  margin-left: 430px; padding-left:85px; margin-right:430px; border-radius:20px 20px; border: 3px solid white; font-size:25px; color: white;">Nghe trên
						${item.tenCongTy}</p>
				</div>
		</div>
	</c:forEach>
	<!-- initialize jQuery Library -->
	<script
		src="<c:url value="/assets/user/plugins/jQuery/jquery.min.js"/>"></script>
	<!-- Bootstrap jQuery -->
	<script
		src="<c:url value="/assets/user/plugins/bootstrap/bootstrap.min.js"/>"
		defer></script>
	<!-- Slick Carousel -->
	<script src="<c:url value="/assets/user/plugins/slick/slick.min.js"/>"></script>
	<script
		src="<c:url value="/assets/user/plugins/slick/slick-animation.min.js"/>"></script>
	<!-- Color box -->
	<script
		src="<c:url value="/assets/user/plugins/colorbox/jquery.colorbox.js"/>"></script>
	<!-- shuffle -->
	<script
		src="<c:url value="/assets/user/plugins/shuffle/shuffle.min.js"/>"
		defer></script>


	<!-- Google Map API Key-->
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCcABaamniA6OL5YvYSpB3pFMNrXwXnLwU"
		defer></script>
	<!-- Google Map Plugin-->
	<script src="<c:url value="/assets/user/plugins/google-map/map.js"/>"
		defer></script>


	<!-- Template custom -->
	<script src="<c:url value="/assets/user/js/script.js"/>"></script>
	<decorator:getProperty property="page.script"></decorator:getProperty>
	</div>
	<!-- Body inner end -->
</body>

</html>
