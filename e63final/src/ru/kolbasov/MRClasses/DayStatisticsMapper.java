package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ru.kolbasov.writables.PriceWritable;

public class DayStatisticsMapper extends
		Mapper<Object, Text, Text, PriceWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] itr = value.toString().split(";");
		// TODO: add data check

		String ticker = itr[0];
		PriceWritable price = new PriceWritable(itr[5], itr[6], itr[7]);

		context.write(new Text(ticker + ";" + itr[2] + ";"), price);

	}
}