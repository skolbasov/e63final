package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.writables.CorrelationWritable;
import ru.kolbasov.writables.TickerStatWritable;

public class CorrelationReducer extends
		Reducer<LongWritable, CorrelationWritable, LongWritable, TickerStatWritable> {

	public void reduce(LongWritable key, Iterable<CorrelationWritable> values, Context context)
			throws IOException, InterruptedException {
		
		context.write(key, new TickerStatWritable(values));
		
	}

}
