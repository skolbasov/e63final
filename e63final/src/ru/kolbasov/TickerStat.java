package ru.kolbasov;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.hadoop.io.Writable;

public class TickerStat implements Writable {
	private Double highestPriceForAnalysisTime;
	private Double lowestPriceForAnalysisTime;
	private Double closePrice;
	private Double medianPrice;
	
	private Double meanPrice;
	private Date beginTime;
	private Date endTime;
	private SimpleDateFormat printFormatter = new SimpleDateFormat(
			"dd-MM-yyyy HH-mm-ss");
	public TickerStat() {

	}

	public TickerStat(Iterable<PriceWritable> prices) {
		DescriptiveStatistics stats=new DescriptiveStatistics();
		ArrayList<Double> closeStats=new ArrayList<Double>();
		ArrayList<Date> dates=new ArrayList<Date>();
		for(PriceWritable price:prices){
			stats.addValue(price.getPrice().getHighPrice());
			stats.addValue(price.getPrice().getClosePrice());
			stats.addValue(price.getPrice().getLowPrice());
			closeStats.add(price.getPrice().getClosePrice());
			dates.add(price.getTimeslot());
		}
		
	
		
		this.highestPriceForAnalysisTime = stats.getMax();
		this.lowestPriceForAnalysisTime = stats.getMin();
		this.closePrice = closeStats.get(closeStats.size()-1);
		this.medianPrice = stats.getPercentile(50);//Median is the 50th percentile
		this.meanPrice = stats.getMean();
		this.beginTime = Collections.min(dates);
		this.endTime = Collections.max(dates);

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		highestPriceForAnalysisTime = in.readDouble();
		lowestPriceForAnalysisTime = in.readDouble();
		closePrice = in.readDouble();
		medianPrice = in.readDouble();
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
				+ medianPrice + " Mean price" + meanPrice;
	}

}
