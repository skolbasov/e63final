package ru.kolbasov.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import ru.kolbasov.auxiliaryClasses.Price;

public class CorrelationWritable implements Writable {
	private String ticker;
	private Price price=new Price();

	public String getTicker() {
		return ticker.toString();
	}

	public void setTicker(String ticker) {
		
		this.ticker=ticker ;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	//private SimpleDateFormat printFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
//	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public CorrelationWritable() {
		
	}
	
	public CorrelationWritable(String highPrice,String lowPrice, String closePrice,String ticker) {
		this.price=new Price(Double.parseDouble(highPrice),Double.parseDouble(lowPrice),Double.parseDouble(closePrice));
		
		setTicker(ticker);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		
		this.price.setHighPrice(in.readDouble());
		this.price.setLowPrice(in.readDouble());
		this.price.setClosePrice(in.readDouble());
		this.setTicker(in.readUTF());
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.price.getHighPrice());
		out.writeDouble(this.price.getLowPrice());
		out.writeDouble(this.price.getClosePrice());
		
		out.writeUTF(this.ticker);
		
	}

	@Override
	public String toString() {
		return "CorrelationWritable [ticker " + ticker + ", price=" + price + "]";
	}
	
    public static CorrelationWritable read(DataInput in) throws IOException {
        CorrelationWritable w = new CorrelationWritable();
        w.readFields(in);
        return w;
      }




}
