package ru.kolbasov.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Writable;

import ru.kolbasov.auxiliaryClasses.StockDate;

public class TickerStatWritable implements Writable {

	private Double beta;
	private Double usdCorrelation;
	private Double eurCorrelation;

	private class priceTime {

		@SuppressWarnings("unused")
		Double price;
		@SuppressWarnings("unused")
		StockDate time;
		public priceTime(Double price, StockDate time) {
			super();
			this.price = price;
			this.time = time;
		}


	}

	public TickerStatWritable(Iterable<CorrelationWritable> values) {
		// TODO Auto-generated constructor stub
		ArrayList<priceTime> usdClosePrices = new ArrayList<priceTime>();
		ArrayList<priceTime> eurClosePrices = new ArrayList<priceTime>();
		ArrayList<priceTime> micexClosePrices = new ArrayList<priceTime>();
		for (CorrelationWritable value : values) {
			if (value.getTicker().contains("USDRUB_TOD")) {
				usdClosePrices.add(new priceTime(value.getPrice()
						.getClosePrice(), value.getTime()));
			}
			if (value.getTicker().contains("Индекс ММВБ")) {
				micexClosePrices.add(new priceTime(value.getPrice()
						.getClosePrice(), value.getTime()));
			}
			if (value.getTicker().contains("EURRUB_TOD")) {
				eurClosePrices.add(new priceTime(value.getPrice()
						.getClosePrice(), value.getTime()));
			}
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		this.beta = in.readDouble();
		this.usdCorrelation = in.readDouble();
		this.eurCorrelation = in.readDouble();

	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeDouble(this.beta);
		out.writeDouble(this.usdCorrelation);
		out.writeDouble(this.eurCorrelation);

	}

}
