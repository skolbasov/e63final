package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ru.kolbasov.writables.PriceWritable;

public class StrategyCheckMapper extends Mapper<Object, Text, Text, PriceWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String val=value.toString().trim();
		String[] itr = val.split(";");
		// TODO: add data check

		String ticker = itr[0];
		PriceWritable price = new PriceWritable(itr[2], itr[3], itr[4], itr[1]);
		// System.out.println(price);
		// int volume = Integer.parseInt(itr[8]);

		context.write(new Text(ticker), price);

		// }
	}
}