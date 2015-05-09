package ru.kolbasov.auxiliaryClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.hadoop.io.Writable;

import ru.kolbasov.writables.PriceWritable;

public class TickerStat implements Writable {
	private Double highestPriceForAnalysisTime;
	private Double lowestPriceForAnalysisTime;
	private Double closePrice;
	private Double medianPrice;
	private Double meanPrice;
	private StockDate beginTime;
	private StockDate endTime;
	DecimalFormat df = new DecimalFormat("#.######");
	public TickerStat() {

	}
    public static TickerStat read(DataInput in) throws IOException {
        TickerStat w = new TickerStat();
        w.readFields(in);
        return w;
      }
	public TickerStat(Iterable<PriceWritable> prices) {
		DescriptiveStatistics stats=new DescriptiveStatistics();
		ArrayList<Double> closeStats=new ArrayList<Double>();
		ArrayList<StockDate> dates=new ArrayList<StockDate>();
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
		this.beginTime = Collections.min(dates, new StockDateComp());
		this.endTime = Collections.max(dates, new StockDateComp());

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.highestPriceForAnalysisTime = in.readDouble();
		this.lowestPriceForAnalysisTime = in.readDouble();
		this.closePrice = in.readDouble();
		this.medianPrice = in.readDouble();
		this.meanPrice = in.readDouble();
		this.beginTime = new StockDate(in.readLong());
		this.endTime = new StockDate(in.readLong());
	}

	@Override
	public String toString() {
		return "TickerStat [beginTime=" + beginTime.toString() + ", endTime=" + endTime.toString()
				+ ", highest price for analysis time is "
				+ highestPriceForAnalysisTime + ", lowest price for analysis time is "
				+ lowestPriceForAnalysisTime + ", close price is " + closePrice
				+ ", median " + df.format(medianPrice) + ", mean " + df.format(meanPrice)
				+ "]";
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.highestPriceForAnalysisTime);
		out.writeDouble(this.lowestPriceForAnalysisTime);
		out.writeDouble(this.closePrice);
		out.writeDouble(this.medianPrice);
		out.writeDouble(this.meanPrice);
		out.writeLong(this.beginTime.getTime());
		out.writeLong(this.endTime.getTime());
	}

}
