package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ru.kolbasov.auxiliaryClasses.StockDate;
import ru.kolbasov.writables.CorrelationWritable;

public class CorrelationMapper extends
		Mapper<Object, Text, LongWritable, CorrelationWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] itr = value.toString().split(";");
		// TODO: add data check
		CorrelationWritable correlationData = new CorrelationWritable(itr[5], itr[6], itr[7], itr[0]);
		
		StockDate stockDate=new StockDate(itr[2]);
		//System.out.println(date.getTime()+" "+correlationData);
		
		//System.out.println(new Text(stockDate.getTime().toString()).toString());
		context.write(new LongWritable(stockDate.getTime()), correlationData);

	}
}