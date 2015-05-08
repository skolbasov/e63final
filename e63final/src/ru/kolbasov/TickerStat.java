package ru.kolbasov;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class TickerStat implements Writable {
	Double highestPriceForAnalysisTime;
	Double lowestPriceForAnalysisTime;
	Double closePrice;
	Double medianPrice;
	Double beta;
	Double meanPrice;
	Date beginTime;
	Date endTime;
	SimpleDateFormat printFormatter = new SimpleDateFormat(
			"dd-MM-yyyy HH-mm-ss");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

	public TickerStat() {

	}

	public TickerStat(Double highestPriceForAnalysisTime,
			Double lowestPriceForAnalysisTime, Double closePrice,
			Double medianPrice, Double beta, Double meanPrice, Date beginTime,
			Date endTime) {
		this.highestPriceForAnalysisTime = highestPriceForAnalysisTime;
		this.lowestPriceForAnalysisTime = lowestPriceForAnalysisTime;
		this.closePrice = closePrice;
		this.medianPrice = medianPrice;
		this.beta = beta;
		this.meanPrice = meanPrice;
		this.beginTime = beginTime;
		this.endTime = endTime;

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		highestPriceForAnalysisTime = in.readDouble();
		lowestPriceForAnalysisTime = in.readDouble();
		closePrice = in.readDouble();
		medianPrice = in.readDouble();
		beta = in.readDouble();
		meanPrice = in.readDouble();
		beginTime = new Date(in.readLong());
		endTime = new Date(in.readLong());
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(highestPriceForAnalysisTime);
		out.writeDouble(lowestPriceForAnalysisTime);
		out.writeDouble(closePrice);
		out.writeDouble(medianPrice);
		out.writeDouble(beta);
		out.writeDouble(meanPrice);
		out.writeLong(beginTime.getTime());
		out.writeLong(endTime.getTime());
	}

	@Override
	public String toString() {

		return "Begin time:" + printFormatter.format(this.beginTime)
				+ " End time:" + printFormatter.format(this.endTime)
				+ " Highest price:" + this.highestPriceForAnalysisTime
				+ " Lowest price:" + this.lowestPriceForAnalysisTime
				+ " Close price:" + this.closePrice + " Median price"
				+ medianPrice + " Mean price" + meanPrice + " Beta" + beta;
	}

}
