package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ru.kolbasov.auxiliaryClasses.Variables;

public class StrategyCheckResultsMapper extends
		Mapper<Object, Text, Text, IntWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String val = value.toString().trim();
		String[] itr = val.split(Variables._DELIM);
		if (itr.length > 1) {
			context.write(new Text(itr[1]), new IntWritable(1));
		}

	}
}