package ru.kolbasov;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TickerStatMapper extends Mapper<Object, Text, Text, PriceWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] itr = value.toString().split(";");
		// TODO: add data check

		// System.out.println(itr[5]+" "+itr[6]+" "+itr[7]);
		// itr[5].
		// if (itr[5].matches("//[0-9,]")
		// && itr[6].matches("//[0-9,]")
		// && itr[7].matches("//[0-9,]")) {
		// System.out.println(itr[7]);
		String ticker = itr[0];
		PriceWritable price = new PriceWritable(itr[5], itr[6], itr[7], itr[2],
				itr[3]);
		// System.out.println(price);
		// int volume = Integer.parseInt(itr[8]);

		context.write(new Text(ticker), price);

		// }
	}
}