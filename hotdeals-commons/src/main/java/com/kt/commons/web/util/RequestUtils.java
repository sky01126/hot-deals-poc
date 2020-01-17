/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.regexp.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kthcorp.commons.lang.ArrayUtils;
import com.kthcorp.commons.lang.StringUtils;

/**
 * HTTP Request 시 사용될 유틸리티
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
public class RequestUtils {

	private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

	private RequestUtils() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

	/**
	 * 브라우저가 Internet Explorer 인지 체크한다.
	 *
	 * @param request the http servlet request
	 * @return true / false
	 */
	public static final boolean isIE(final HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return isIE(userAgent);
	}

	/**
	 * 브라우저가 Internet Explorer 인지 체크한다.
	 *
	 * @param userAgent the HTTP User-Agent
	 * @return true / false
	 */
	public static final boolean isIE(final String userAgent) {
		if (StringUtils.isBlank(userAgent)) {
			return false;
		} else if (StringUtils.containsIgnoreCase(userAgent, "MSIE")
				|| StringUtils.containsIgnoreCase(userAgent, "Trident")) {
			return true;
		}
		return false;
	}

	/**
	 * 모바일에서 접속했는지 확인한다.
	 *
	 * @param request HttpServletRequest
	 * @return true / false
	 */
	public static final boolean isMobile(final HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return isMobile(userAgent);
	}

	/**
	 * 모바일에서 접속했는지 확인한다.
	 *
	 * @param userAgent the HTTP User-Agent
	 * @return true / false
	 */
	@SuppressWarnings("all")
	public static final boolean isMobile(final String userAgent) {
		if (StringUtils.isNotBlank(userAgent) && userAgent.matches(
				"(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")
				|| userAgent.substring(0, 4).matches(
						"(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
			return true;
		}
		return false;
	}

	/**
	 * 확장자를 이용해서 Resource 여부 확인.
	 *
	 * @param str 비교할 String
	 * @return true / false
	 */
	@SuppressWarnings("all")
	public static final boolean isResource(final String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		String allowPattern = ".+\\.(jpe?g|gif|png|bmp|ico|css|js|ttf|otf|eot|woff|woff2|pdf|zip|htm|html|docx?|xlsx?|pptx?|txt|wav|swf|svg|avi|mp\\d)$";
		Pattern pattern = Pattern.compile(allowPattern);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 확장자를 이용해서 Resource 여부 확인.
	 *
	 * @param str 비교할 String
	 * @return true / false
	 */
	public static final boolean isNotResource(final String str) {
		return !isResource(str);
	}

	/**
	 * 접속 클라이언트 아이피 정보 조회
	 *
	 * @param request the http servlet request
	 * @return the remote client ip address
	 */
	public static String remoteAddr(final HttpServletRequest request) {
		if (StringUtils.isNotEmpty(request.getHeader("NS-CLIENT-IP"))) {
			log.debug("Remote Address Header - NS-CLIENT-IP");
			return request.getHeader("NS-CLIENT-IP");
		} else if (StringUtils.isNotEmpty(request.getHeader("X-Forwarded-For"))) {
			log.debug("Remote Address Header - X-Forwarded-For");
			return request.getHeader("X-Forwarded-For");
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * HttpServletRequest 를 기반으로 요청 도메인을 port 와 더불어 반환 한다.
	 *
	 * @param request the http servlet request
	 * @return the http server url
	 */
	public static String requestServer(final HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		if (request.getServerPort() == 80) {
			sb.append("http://").append(request.getServerName());
		} else if (request.getServerPort() == 443) {
			sb.append("https://").append(request.getServerName());
		} else {
			sb.append("http://").append(request.getServerName());
			sb.append(":").append(request.getServerPort());
		}
		return sb.toString();
	}

	/**
	 * Referer 정보 조회
	 *
	 * @param request HttpServletRequest
	 * @return Referer
	 */
	public static String referer(final HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (StringUtils.isBlank(referer)) {
			return null;
		}
		return StringUtils.replace(referer, requestServer(request), "");
	}

	/**
	 * 클라이언트 아이피 주소가 접근 허용/제한 여부 확인.
	 *
	 * <pre>
	 * <code>
	 * List&#60;String&#62; list = new ArrayList&#60;&#62;();
	 * list.add("127.0");
	 * list.add("127.1.1.*");
	 * boolean allow = RequestUtils.ipFilter(HttpServletRequest, list);
	 * </code>
	 * </pre>
	 *
	 * @param request the http servlet request
	 * @param ipList the ip filter list
	 * @return true (IP List에 Remote Address 존재) / false (IP List에 Remote Address 없음)
	 */
	public static boolean ipFilter(final HttpServletRequest request, final List<String> ipList) {
		return ipFilter(remoteAddr(request), ipList);
	}

	/**
	 * 클라이언트 아이피 주소가 접근 허용/제한 여부 확인.
	 *
	 * <pre>
	 * <code>
	 * List&#60;String&#62; list = new ArrayList&#60;&#62;();
	 * list.add("127.0");
	 * list.add("127.1.1.*");
	 * boolean allow = RequestUtils.ipFilter("127.1.1.1", list);
	 * </code>
	 * </pre>
	 *
	 * @param remoteAddr the remote client ip address
	 * @param ipList the ip filter list
	 * @return true (IP List에 Remote Address 존재) / false (IP List에 Remote Address 없음)
	 */
	public static boolean ipFilter(final String remoteAddr, final List<String> ipList) {
		if (StringUtils.isBlank(remoteAddr)) {
			throw new IllegalArgumentException("Remote Address 정보가 없습니다.");
		} else if (ArrayUtils.isEmpty(ipList)) {
			throw new IllegalArgumentException("IP 리스트 정보가 없습니다.");
		}

		// IP List를 RE로 변환해서 다시 리스트에 담는다.
		List<RE> reList = new ArrayList<>();
		for (String ip : ipList) {
			if (StringUtils.isNotBlank(ip)) {
				reList.add(new RE(StringUtils.trim(ip)));
			}
		}

		// RE에 IP List를 담는다.
		RE[] allows = reList.toArray(new RE[reList.size()]);
		for (RE allow : allows) {
			if (allow.match(remoteAddr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Request에서 Parameter를 가져온다.
	 *
	 * @param request the http servlet request
	 * @param name the parameter name
	 * @return the parameter value
	 */
	public static final String getParam(final HttpServletRequest request, final String name) {
		return request.getParameter(name);
	}

	/**
	 * Cookie 정보를 가져온다.
	 *
	 * @param request the servlet request
	 * @param name teh cookie name
	 * @return the cookie
	 */
	public static final Cookie getCookie(final ServletRequest request, final String name) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return getCookie(httpRequest, name);
	}

	/**
	 * Cookie 정보를 가져온다.
	 *
	 * @param request the http servlet request
	 * @param name teh cookie name
	 * @return the cookie
	 */
	public static final Cookie getCookie(final HttpServletRequest request, final String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i];
			}
		}
		return null;
	}

}
