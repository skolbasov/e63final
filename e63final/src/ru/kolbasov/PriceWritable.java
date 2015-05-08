package ru.kolbasov;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class PriceWritable implements Writable {
	Double highPrice;
    Double lowPrice;
    Double closePrice;
	public PriceWritable() {
		// TODO Auto-generated constructor stub
		
	}
	
	public PriceWritable(String highPrice,String lowPrice, String closePrice) {
		// TODO Auto-generated constructor stub
		this.highPrice=Double.parseDouble(highPrice);
		this.lowPrice=Double.parseDouble(lowPrice);
		this.closePrice=Double.parseDouble(closePrice);
		
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
        return "High price:"+this.highPrice + " Low price:" + this.lowPrice + " Close price:" + this.closePrice;
    }

}
