package ru.kolbasov;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class PriceWritable implements Writable {
	Double highPrice;
    Double lowPrice;
    Double closePrice;
    Date timeslot;
    SimpleDateFormat printFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public PriceWritable() {
		
	}
	
	public PriceWritable(String highPrice,String lowPrice, String closePrice,String date, String time) {
		this.highPrice=Double.parseDouble(highPrice);
		this.lowPrice=Double.parseDouble(lowPrice);
		this.closePrice=Double.parseDouble(closePrice);
		
		try {
			date=date.concat(time);
			this.timeslot=formatter.parse(date);
		//	System.out.println(printFormatter.format(this.timeslot));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.highPrice=in.readDouble();
		this.lowPrice=in.readDouble();
		this.closePrice=in.readDouble();
		this.timeslot = new Date(in.readLong());
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.highPrice);
		out.writeDouble(this.lowPrice);
		out.writeDouble(this.closePrice);
		out.writeLong(this.timeslot.getTime());
		
	//	System.out.println("write completed");
	}
	
    public String toString() {
    	
        return "Timeslot:"+printFormatter.format(this.timeslot)+" Highest price:"+this.highPrice + " Lowest price:" + this.lowPrice + " Close price:" + this.closePrice;
    }

}
