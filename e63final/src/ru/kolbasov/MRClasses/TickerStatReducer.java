package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.auxiliaryClasses.Variables;
import ru.kolbasov.writables.FullTickerStatWritable;
import ru.kolbasov.writables.PriceWritable;

public class TickerStatReducer extends
		Reducer<Text, PriceWritable, Text, FullTickerStatWritable> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
		FullTickerStatWritable ticker = new FullTickerStatWritable(values);
		context.write(new Text(key.toString()+Variables._DELIM), ticker);
	}

}