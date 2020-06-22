package com.apache.pfcalculator.proxyservice.utils;

public class CommonUtils {
	
	private static final String UPLOAD_SERVICE = "upload";
	
	private static final String VIEWER_SERVICE = "viewer";
	
	private static final String USER_SERVICE = "user";
	
	
	public static String findServiceBasedOnUri(String uri)
	{
		if(uri.contains(UPLOAD_SERVICE))
		{
			return "upload_service";
		}
		else if(uri.contains(VIEWER_SERVICE))
		{
			return "viewer_service";
		}
		else if(uri.contains(USER_SERVICE))
		{
			return "user";
		}
		
		return null;
	}

}
