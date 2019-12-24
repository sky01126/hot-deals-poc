<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:import url="./error/commons_error.jsp" charEncoding="UTF-8">
    <c:param name="title" value="Internal Server Error" />
    <c:param name="errorNumber" value="500" />
    <c:param name="errorMessage" value="We track these errors automatically, but if the problem persists feel free to contact us. In the meantime, try refreshing." />
</c:import>
</body>
</html>
