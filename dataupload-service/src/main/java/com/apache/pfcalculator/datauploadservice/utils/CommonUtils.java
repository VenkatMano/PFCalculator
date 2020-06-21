package com.apache.pfcalculator.datauploadservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {
	
	public static String convertJsonDateToAcceptedFormat(String jsonDate) throws ParseException
	{
		SimpleDateFormat jsonDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		return formatDate.format(jsonDateFormat.parse(jsonDate));
	}

}
