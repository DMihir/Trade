package com.barclays.trade.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mihir
 *
 */
public class Utility {

	/**
	 * This method will do comparison of provided date with present date.
	 * 
	 * @param compareDate
	 * @return
	 */
	public static int compareDateWithPresentDate(Date compareDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date()).compareTo(sdf.format(compareDate));
	}
}
