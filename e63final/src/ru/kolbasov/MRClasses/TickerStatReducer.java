package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.auxiliaryClasses.TickerStat;
import ru.kolbasov.writables.PriceWritable;

public class TickerStatReducer extends
		Reducer<Text, PriceWritable, Text, TickerStat> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
		TickerStat ticker = new TickerStat(values);
		context.write(key, ticker);
	}

}