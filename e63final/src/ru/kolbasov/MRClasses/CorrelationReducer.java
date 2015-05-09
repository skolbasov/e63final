package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.writables.CorrelationWritable;

public class CorrelationReducer extends
		Reducer<LongWritable, CorrelationWritable, Text, CorrelationWritable> {

	public void reduce(Text key, Iterable<CorrelationWritable> values, Context context)
			throws IOException, InterruptedException {
		for(CorrelationWritable value:values){
		context.write(key, value);
		}
	}

}
