package ru.kolbasov.MRClasses;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ru.kolbasov.auxiliaryClasses.StrategyTicker;
import ru.kolbasov.auxiliaryClasses.StrategyTickerStat;
import ru.kolbasov.writables.PriceWritable;

public class StrategyCheckReducer extends
		Reducer<Text, PriceWritable, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<PriceWritable> values, Context context)
			throws IOException, InterruptedException {

		StrategyTickerStat strategyStat = new StrategyTickerStat(
				values);
		for (StrategyTicker value : strategyStat.getStrategyRecomendations()) {
			if (!value.toWrite().isEmpty()) {
				context.write(key, new Text(value.toWrite()));
			}
		}

	}

}