package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinStatisticsReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		// TODO Think about the lowest price comparison
		String tempStr="";
		for(Text value:values){
			if (value.toString().contains("TickerStat")){
				tempStr=value.toString().concat(tempStr);
			}
			else tempStr=tempStr.concat(value.toString());
		}
		context.write(key, new Text(tempStr));

	}

}
