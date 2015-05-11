package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StrategyCheckResultsReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		for (@SuppressWarnings("unused") IntWritable iterator : values) {
			count++;
		}

		// _ is a separator for next mapper
		context.write(key, new IntWritable(count));

	}

}