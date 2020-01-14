<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:if test="${timeout ne null}">
<meta http-equiv='refresh' content='${timeout}; url=<c:out value="${url}" />'></meta>
</c:if>
<c:import url="commons_error.jsp" charEncoding="UTF-8">
	<c:param name="title"        value="Bad Request" />
	<c:param name="errorNumber"  value="400" />
	<c:param name="errorMessage" value="서버가 요청의 구문을 인식하지 못했습닌다." />
</c:import>
</body>
</html>

