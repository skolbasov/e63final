package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.auxiliaryClasses.Price;
import ru.kolbasov.writables.CorrelationWritable;
import ru.kolbasov.writables.PriceWritable;
import ru.kolbasov.writables.TickerStatWritable;

public class DayStatisticsReducer extends
		Reducer<Text, PriceWritable, Text, Text> {

	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
			//TODO Think about the lowest price comparison
			Price price=new Price(0d,999999999999d,0d);
		for(PriceWritable value:values){
			if (price.getHighPrice()<value.getPrice().getHighPrice()){
				price.setHighPrice(value.getPrice().getHighPrice());
			}
			if (price.getLowPrice()>value.getPrice().getLowPrice()){
				price.setLowPrice(value.getPrice().getLowPrice());
			}
			price.setClosePrice(value.getPrice().getClosePrice());
			
		}
		context.write(key,new Text(price.toWriteSimple()));
		
		
	}

}
