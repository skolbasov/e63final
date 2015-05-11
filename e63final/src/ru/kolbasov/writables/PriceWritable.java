package ru.kolbasov.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import ru.kolbasov.auxiliaryClasses.Price;
import ru.kolbasov.auxiliaryClasses.StockDate;

public class PriceWritable implements Writable {
	private StockDate timeslot;
	private Price price=new Price();
	public StockDate getTimeslot() {
		return timeslot;
	}

	public Long getTimeslotInLong() {
		return timeslot.getTime();
	}
	

	public void setTimeslot(StockDate timeslot) {
		this.timeslot = timeslot;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}


	public PriceWritable() {
		
	}
	
	public PriceWritable(String highPrice,String lowPrice, String closePrice,String date, String time) {
		this.price=new Price(Double.parseDouble(highPrice),Double.parseDouble(lowPrice),Double.parseDouble(closePrice));
		this.timeslot=new StockDate(date,time);

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		
		this.price.setHighPrice(in.readDouble());
		this.price.setLowPrice(in.readDouble());
		this.price.setClosePrice(in.readDouble());
		this.timeslot = new StockDate(in.readLong());
		
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
		return "PriceWritable [timeslot=" + timeslot.toString() + ", price=" + price + "]";
	}
	
    public static PriceWritable read(DataInput in) throws IOException {
        PriceWritable w = new PriceWritable();
        w.readFields(in);
        return w;
      }




}
