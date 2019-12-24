<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${param.title}</title>
<!-- Vendor styles -->
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.css" />" />
<link rel="stylesheet" href="<c:url value="/static/error/font-awesome/css/font-awesome.min.css" />" />
<link rel="stylesheet" href="<c:url value="/static/error/nprogress/nprogress.css" />" />
<!-- Custom Theme Style -->
<link rel="stylesheet" href="<c:url value="/static/error/custom.min.css" />" />
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <!-- page content -->
        <div class="col-md-12">
            <div class="col-middle">
                <div class="text-center text-center">
                    <h1 class="error-number">${param.errorNumber}</h1>
                    <h2>${param.errorMessage}</h2>

                </div>
            </div>
        </div>
        <!-- /page content -->
    </div>
</div>
<!-- Vendor scripts -->
<script src="<c:url value="/webjars/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/static/error/fastclick/lib/fastclick.js" />"></script>
<script src="<c:url value="/static/error/nprogress/nprogress.js" />"></script>
<!-- Custom Theme Scripts -->
<script src="<c:url value="/static/error/custom.min.js" />"></script>