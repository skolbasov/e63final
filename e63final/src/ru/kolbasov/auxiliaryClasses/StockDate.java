package ru.kolbasov.auxiliaryClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockDate implements Comparable<StockDate> {
	private Date date;
	//DateFormatter for parsing the String with the Date
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	//DateFormatter for parsing the String with the Time
	private SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss");
	//DateFormatter for output
	private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	//DateFormatter for output
	private SimpleDateFormat printFormatter = new SimpleDateFormat(
			"dd-MM-yyyy HH-mm-ss");
/**
 * Simple constructor
 */
	public StockDate() {
		// TODO Auto-generated constructor stub
		this.date = new Date();
	}
/**
 * This Constructor uses time in milliseconds as an input
 * @param date
 */
	public StockDate(Long date) {
		this.date = new Date(date);
	}
/**
 * This Constructor uses another date as an input
 * @param date
 */
	public StockDate(StockDate date) {
		this.date = new Date(date.getTime());
	}

	/**
	 * This Constructor creates another StockDate using String date and boolean value whether the String provided is time or not
	 * @param date
	 */	
	public StockDate(String date, Boolean isTime) {
		try {
			if (isTime) {
				this.date = timeFormatter.parse(date);
			} else {
				this.date = dateFormatter.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This Constructor creates another StockDate using String date and String time
	 * @param date
	 */	
	public StockDate(String date, String time) {

		try {
			date = date.concat(time);
			this.date = dateTimeFormatter.parse(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getDate() {
		return this.date;
	}

	public Long getTime() {
		return this.date.getTime();
	}

	@Override
	public String toString() {
		return printFormatter.format(this.date);
	}

	public String toStringDateOnly() {
		return dateFormatter.format(this.date);
	}

	@Override
	public int compareTo(StockDate date) {

		return new Integer(Math.round(this.date.getTime() - date.getTime()));
	}

}
