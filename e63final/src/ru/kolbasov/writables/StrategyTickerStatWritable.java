package ru.kolbasov.writables;
//TODO: add logs using log4j
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.hadoop.io.Writable;

import com.google.common.collect.Lists;

import ru.kolbasov.auxiliaryClasses.PriceWritableCompByDate;
import ru.kolbasov.auxiliaryClasses.StockDate;
import ru.kolbasov.auxiliaryClasses.StockDateComp;
import ru.kolbasov.auxiliaryClasses.StrategyTicker;

public class StrategyTickerStatWritable implements Writable {
	final Long _ANALYSISFRAME=30L;//30 calendar days period is close to 20 trading days period
	private ArrayList<StrategyTicker> strategyRecomendations=new ArrayList<StrategyTicker>();
	@Override
	public String toString() {
		return "StrategyTickerStatWritable [strategyRecomendations="
				+ strategyRecomendations + "]";
	}
	
	public String toWrite() {
		String tempString="";
		
		for(StrategyTicker recomendation:this.strategyRecomendations){
			if (!recomendation.toWrite().isEmpty()){
				tempString=tempString.concat(recomendation.toWrite());
			}
		}
		if (tempString.isEmpty()){ return "";}
		
		else {
			return "AVAILABLE:".concat(tempString);
					}
	}

	DecimalFormat df = new DecimalFormat("#.######");
	public StrategyTickerStatWritable() {

	}
	
    public static StrategyTickerStatWritable read(DataInput in) throws IOException {
        StrategyTickerStatWritable w = new StrategyTickerStatWritable();
        w.readFields(in);
        return w;
      }
	public StrategyTickerStatWritable(Iterable<PriceWritable> prices) {
		//System.out.println("StrategyTickerWritable run");
		ArrayList<PriceWritable> pricesList=new ArrayList<PriceWritable>();
		for(PriceWritable price:prices){
			PriceWritable pw=new PriceWritable(price);
			pricesList.add(pw);
			
		}
//Arrange values by days
		Collections.sort(pricesList,new PriceWritableCompByDate());

		int iterator=-1;	
		for(PriceWritable price:pricesList){
			ArrayList<PriceWritable> strategyPrices=new ArrayList<PriceWritable>();
			iterator++;
			System.out.println(price.getTimeslotInLong());
			Long analysisFrameEnd=price.getTimeslotInLong()+(86400000*_ANALYSISFRAME);
			//Check whether the priceList has necessary amount of days for analysis. 86400000-milliseconds in day
			if (pricesList.get(pricesList.size()-1).getTimeslotInLong()>analysisFrameEnd){
				int i=0;
			while (pricesList.get(iterator+i).getTimeslotInLong()<analysisFrameEnd){
				strategyPrices.add(pricesList.get(i+iterator));
				i++;
			}
			strategyRecomendations.add(new StrategyTicker(strategyPrices));
			strategyPrices.clear();		}
			else{
				System.out.println("Not enough data left to analyse with timeframe "+_ANALYSISFRAME+" days");
			}
		}
	//	System.out.println(strategyRecomendations);
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		
	}

	

	@Override
	public void write(DataOutput out) throws IOException {

	}

}
