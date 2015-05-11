package ru.kolbasov.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import ru.kolbasov.auxiliaryClasses.Price;
import ru.kolbasov.auxiliaryClasses.StockDate;

public class PriceWritable implements Writable {
	private StockDate timeslot;
	private Price price;

	public StockDate getTimeslot() {
		return this.timeslot;
	}
	
	public Double getHighPrice(){
		return this.price.getHighPrice();
	}
	
	public Double getLowPrice(){
		return this.price.getLowPrice();
	}
	
	public Double getClosePrice(){
		return this.price.getClosePrice();
	}
	

	public Long getTimeslotInLong() {
		return this.timeslot.getTime();
	}

	public void setTimeslot(StockDate timeslot) {
		this.timeslot = timeslot;
	}
@Deprecated
	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public PriceWritable() {
		this.price = new Price();
		this.timeslot = new StockDate();
	}

	public PriceWritable(String highPrice, String lowPrice, String closePrice,
			String date, String time) {
		this.price = new Price(Double.parseDouble(highPrice),
				Double.parseDouble(lowPrice), Double.parseDouble(closePrice));
		this.timeslot = new StockDate(date, time);

	}

	public PriceWritable(String highPrice, String lowPrice, String closePrice,
			String date) {
		this.price = new Price(Double.parseDouble(highPrice),
				Double.parseDouble(lowPrice), Double.parseDouble(closePrice));
		this.timeslot = new StockDate(date, false);

	}

	public PriceWritable(String highPrice, String lowPrice, String closePrice) {
		this.price = new Price(Double.parseDouble(highPrice),
				Double.parseDouble(lowPrice), Double.parseDouble(closePrice));
		this.timeslot = new StockDate();

	}

	public PriceWritable(PriceWritable price) {
		this.price = new Price(price.getHighPrice(), price.getLowPrice(), price.getClosePrice());
		this.timeslot = new StockDate(price.getTimeslot());

	}

	public PriceWritable(Double highPrice, Double lowPrice, Double closePrice,
			StockDate dateTime) {
		this.price = new Price(highPrice, lowPrice, closePrice);
		this.setTimeslot(dateTime);
		;

	}

	public PriceWritable(Double highPrice, Double lowPrice, Double closePrice) {
		this.price = new Price(highPrice, lowPrice, closePrice);
		this.setTimeslot(new StockDate());
		;

	}

	public PriceWritable(Double highPrice, Double lowPrice, Double closePrice,
			Long dateTime) {
		this.price = new Price(highPrice, lowPrice, closePrice);
		this.setTimeslot(new StockDate(dateTime));
		;

	}

	public PriceWritable(Price price, StockDate date) {
		this.setPrice(price);
		this.setTimeslot(date);

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
		return "PriceWritable [timeslot=" + this.timeslot.toString()
				+ ", price=" + this.price + "]";
	}

	public static PriceWritable read(DataInput in) throws IOException {

		PriceWritable w = new PriceWritable();
		w.readFields(in);
		return w;
	}

}
