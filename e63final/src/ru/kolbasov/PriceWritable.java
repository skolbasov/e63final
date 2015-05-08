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
		// TODO Auto-generated constructor stub
		
	}
	
	public PriceWritable(String highPrice,String lowPrice, String closePrice,String date, String time) {
		// TODO Auto-generated constructor stub
		this.highPrice=Double.parseDouble(highPrice);
		this.lowPrice=Double.parseDouble(lowPrice);
		this.closePrice=Double.parseDouble(closePrice);
		
		try {
			this.timeslot=formatter.parse(date.concat(time));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		highPrice=in.readDouble();
		lowPrice=in.readDouble();
		closePrice=in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeDouble(highPrice);
		out.writeDouble(lowPrice);
		out.writeDouble(closePrice);
	}
	
    public String toString() {
    	
        return "Timeslot:"+printFormatter.format(this.timeslot)+" Highest price:"+this.highPrice + " Lowest price:" + this.lowPrice + " Close price:" + this.closePrice;
    }

}
