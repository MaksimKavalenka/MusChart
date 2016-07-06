<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/muschart/css/styles.css">
		<script type="text/javascript">
			function setPage(page) {
				document.getElementById('page').value = page;
				document.getElementById('form').submit();
			}
		</script>
	</head>
	<body>
		<input type="hidden" id="page" name="page" value="1">
		<c:choose>
			<c:when test="${page < 3}">
				<c:set var="from" value="1"/>
				<c:set var="to" value="5"/>
			</c:when>
			<c:otherwise>
				<c:set var="from" value="${page - 2}"/>
				<c:set var="to" value="${page + 2}"/>
			</c:otherwise>
		</c:choose>
		<ul class="pagination">
			<c:choose>
				<c:when test="${page eq 1}">
					<li class="disabled"><a href="#">&laquo;</a>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:setPage(${page - 1})">&laquo;</a>
				</c:otherwise>
			</c:choose>
			<c:forEach var="i" begin="${from}" end="${to}">
				<c:choose>
					<c:when test="${page eq i}">
						<li class="active"><a href="javascript:setPage(${i})">${i}</a>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:setPage(${i})">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li><a href="javascript:setPage(${page + 1})">&raquo;</a></li>
		</ul>
	</body>
</html>