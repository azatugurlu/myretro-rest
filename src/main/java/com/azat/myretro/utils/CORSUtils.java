package com.azat.myretro.utils;

import javax.servlet.http.HttpServletResponse;

public class CORSUtils {
	
	public static void addCorsSupport(HttpServletResponse servletResponse) {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Experience-API-Version, Authorization");
	}

}
