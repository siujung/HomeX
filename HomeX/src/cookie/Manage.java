package cookie;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Manage {
	public static boolean setCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String newValue) {
		Cookie cookie = getCookie(request, name);
		boolean isNew;

		if (null != cookie) {
			if (!newValue.equals(cookie.getValue()))
				isNew = true;
			else
				isNew = false;

			cookie.setValue(newValue);
			response.addCookie(cookie);
		} else {
			cookie = new Cookie(name, newValue);
			response.addCookie(cookie);
			isNew = true;
		}

		return isNew;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = GetCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else
			return null;
	}

	private static Map<String, Cookie> GetCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}

		return cookieMap;
	}
}
