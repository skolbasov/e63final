package ru.kolbasov;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CorrelationReducer extends
		Reducer<Text, PriceWritable, Text, TickerStat> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
		TickerStat ticker = new TickerStat(values);
		context.write(key, ticker);
	}

}
