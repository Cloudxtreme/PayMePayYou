package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getSimpleFormatted(Date d) {
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy");
		
		String dateStr = d.toString();

		try {
			dateStr = simpleFormat.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dateStr;
	}
	
}
