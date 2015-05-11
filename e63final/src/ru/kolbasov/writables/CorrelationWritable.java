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
import ru.kolbasov.auxiliaryClasses.StockDate;

public class CorrelationWritable implements Writable {
	private String ticker;
	private Price price=new Price();
	private StockDate time;
	public String getTicker() {
		return ticker.toString();
	}

	public void setTicker(String ticker) {		
		this.ticker=ticker ;
	}

	public Price getPrice() {
		return price;
	}

	public StockDate getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = new StockDate(time);
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	//private SimpleDateFormat printFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
//	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public CorrelationWritable() {
		
	}
	
	public CorrelationWritable(String highPrice,String lowPrice, String closePrice,String ticker,String time) {
		this.price=new Price(Double.parseDouble(highPrice),Double.parseDouble(lowPrice),Double.parseDouble(closePrice));
		this.time=new StockDate(time,true);
		setTicker(ticker);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		
		this.price.setHighPrice(in.readDouble());
		this.price.setLowPrice(in.readDouble());
		this.price.setClosePrice(in.readDouble());
		this.setTime(in.readLong());
		this.setTicker(in.readUTF());
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.price.getHighPrice());
		out.writeDouble(this.price.getLowPrice());
		out.writeDouble(this.price.getClosePrice());
		out.writeLong(time.getTime());
		out.writeUTF(this.ticker);
		
	}

	@Override
	public String toString() {
		return "CorrelationWritable [ticker=" + ticker + ", price=" + price
				+ ", time=" + time + "]";
	}
	
    public static CorrelationWritable read(DataInput in) throws IOException {
        CorrelationWritable w = new CorrelationWritable();
        w.readFields(in);
        return w;
      }




}
