package ru.kolbasov.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.hadoop.io.Writable;

import ru.kolbasov.auxiliaryClasses.PriceWritableCompByDate;
import ru.kolbasov.auxiliaryClasses.StockDate;
import ru.kolbasov.auxiliaryClasses.StockDateComp;
import ru.kolbasov.auxiliaryClasses.StrategyTicker;

public class StrategyTickerStatWritable implements Writable {

	
	DecimalFormat df = new DecimalFormat("#.######");
	public StrategyTickerStatWritable() {

	}
	
    public static StrategyTickerStatWritable read(DataInput in) throws IOException {
        StrategyTickerStatWritable w = new StrategyTickerStatWritable();
        w.readFields(in);
        return w;
      }
	public StrategyTickerStatWritable(Iterable<PriceWritable> prices) {
		System.out.println("StrategyTickerWritable run");
		ArrayList<PriceWritable> pricesList=new ArrayList<PriceWritable>();
		for(PriceWritable price:prices){
			pricesList.add(price);
		}
		
		Collections.sort(pricesList,new PriceWritableCompByDate());
	//	System.out.println(pricesList);
		int iterator=-1;
		ArrayList<PriceWritable> strategyPrices=new ArrayList<PriceWritable>();
		ArrayList<StrategyTicker> strategyRecomendations=new ArrayList<StrategyTicker>();
		for(PriceWritable price:pricesList){
			iterator++;
			for(int i=0;i<20;i++){
				if ((iterator+20)<pricesList.size()){
					strategyPrices.add(pricesList.get(i+iterator));
				}
			}
			strategyRecomendations.add(new StrategyTicker(strategyPrices));
			strategyPrices.clear();			
		}
		System.out.println(strategyRecomendations);
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		
	}

	

	@Override
	public void write(DataOutput out) throws IOException {

	}

}
