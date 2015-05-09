package ru.kolbasov;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class PriceWritable implements Writable {
	private Date timeslot;
	private Price price=new Price();


	public Date getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(Date timeslot) {
		this.timeslot = timeslot;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	private SimpleDateFormat printFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public PriceWritable() {
		
	}
	
	public PriceWritable(String highPrice,String lowPrice, String closePrice,String date, String time) {
		this.price=new Price(Double.parseDouble(highPrice),Double.parseDouble(lowPrice),Double.parseDouble(closePrice));
		
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
		
		this.price.setHighPrice(in.readDouble());
		this.price.setLowPrice(in.readDouble());
		this.price.setClosePrice(in.readDouble());
		this.timeslot = new Date(in.readLong());
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.price.getHighPrice());
		out.writeDouble(this.price.getLowPrice());
		out.writeDouble(this.price.getClosePrice());
		out.writeLong(this.timeslot.getTime());
		
	}

	@Override
	public String toString() {
		return "PriceWritable [timeslot=" + printFormatter.format(timeslot) + ", price=" + price + "]";
	}
	
    public static PriceWritable read(DataInput in) throws IOException {
        PriceWritable w = new PriceWritable();
        w.readFields(in);
        return w;
      }




}
