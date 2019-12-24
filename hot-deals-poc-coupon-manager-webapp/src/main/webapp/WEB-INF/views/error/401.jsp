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
	<c:param name="title"        value="Unauthorized" />
	<c:param name="errorNumber"  value="401" />
	<c:param name="errorMessage" value="인증되지 않은 사용자입니다." />
</c:import>
</body>
</html>

