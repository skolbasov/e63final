package ru.kolbasov.auxiliaryClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockDate {
	private Date date;
	private SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat printFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	public StockDate() {
		// TODO Auto-generated constructor stub
		Date date=new Date();
	}

	public StockDate(Long date) {
		this.date=new Date(date);
	}

	public StockDate(String date) {
		try {
			this.date=dateFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public StockDate(String date, String time) {
		
		try {
			date=date.concat(time);
			this.date=dateTimeFormatter.parse(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	public Long getTime(){
		return this.date.getTime();
	}
	
	
	
	@Override
	public String toString() {
		return printFormatter.format(this.date);
	}
		
	
	


}
