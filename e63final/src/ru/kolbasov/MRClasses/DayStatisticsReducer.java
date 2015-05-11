package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.auxiliaryClasses.Price;
import ru.kolbasov.writables.PriceWritable;

public class DayStatisticsReducer extends
		Reducer<Text, PriceWritable, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
		// TODO Think about the lowest price comparison
		Price price = new Price(0d, 999999999999d, 0d);
		for (PriceWritable value : values) {
			if (price.getHighPrice() < value.getHighPrice()) {
				price.setHighPrice(value.getHighPrice());
			}
			if (price.getLowPrice() > value.getLowPrice()) {
				price.setLowPrice(value.getLowPrice());
			}
			price.setClosePrice(value.getClosePrice());

		}
		context.write(key, new Text(price.toWriteSimple()));

	}

}
