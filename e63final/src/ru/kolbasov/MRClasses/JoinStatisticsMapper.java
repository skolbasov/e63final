package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ru.kolbasov.auxiliaryClasses.Variables;

public class JoinStatisticsMapper extends Mapper<Object, Text, Text, Text> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

//		String val = value.toString().trim();
			String val = value.toString();
			String[] itr = val.split(Variables._DELIM);
			if (itr.length > 1) {
				String tempStr="";
				for(int i=1;i<itr.length;i++){
				tempStr=tempStr.concat(itr[i].toString());	
				}
				context.write(new Text(itr[0]), new Text(tempStr));
				}
			}
		
	
	

	
}