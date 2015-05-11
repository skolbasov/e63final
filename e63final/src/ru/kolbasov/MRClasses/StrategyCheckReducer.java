package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.writables.FullTickerStatWritable;
import ru.kolbasov.writables.PriceWritable;
import ru.kolbasov.writables.StrategyTickerStatWritable;

public class StrategyCheckReducer extends
		Reducer<Text, PriceWritable, Text, FullTickerStatWritable> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {
		for(PriceWritable value:values){
			StrategyTickerStatWritable strategyStat=new StrategyTickerStatWritable(values);
		}
	//	System.out.println("reducer run");
	//	context.write(key, ticker);
	}

}