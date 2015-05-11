package ru.kolbasov.auxiliaryClasses;

import java.util.ArrayList;
import java.util.Collections;

import ru.kolbasov.writables.PriceWritable;

public class StrategyTicker {

	private StockDate beginDate=new StockDate();
	private Boolean buy = false;
	private StockDate endDate=new StockDate();
	//TODO think about lowest price
	private Price priceStat=new Price(0d,999999999999d,0d);
	private Boolean sell = false;
	private Boolean wait = false;
	private Boolean proved=false;

	public StrategyTicker(ArrayList<PriceWritable> values) {
		// TODO Auto-generated constructor stub
		if (values.size()>0){
			beginDate = values.get(0).getTimeslot();
		
		endDate = values.get(values.size() - 1).getTimeslot();
		
		priceStat.setHighPrice(Collections.max(values,new PriceWritableCompByHighestPrice()).getPrice().getHighPrice());
		priceStat.setLowPrice(Collections.min(values,new PriceWritableCompByLowPrice()).getPrice().getLowPrice());
		priceStat.setClosePrice(values.get(values.size() - 1).getPrice()
				.getClosePrice());
		if (values.get(values.size()-1).getPrice().getHighPrice() >= priceStat.getHighPrice()) {
			this.buy = true;
		} else if (values.get(values.size()-1).getPrice().getLowPrice() <= priceStat.getLowPrice())
			this.sell = true;
		 else this.wait=true;}
	}


	@Override
	public String toString() {
		return "StrategyTicker [beginDate=" + beginDate.toStringDateOnly() + ", endDate="
				+ endDate.toStringDateOnly() + ", priceStat=" + priceStat + ", buy=" + buy
				+ ", sell=" + sell + ", wait=" + wait + "]";
	}


	public String toWrite() {
		String tempString="";
		if (!this.wait){
			if (buy){
				
			
		tempString="BUY period:" + beginDate.toStringDateOnly() + "-"
				+ endDate.toStringDateOnly() + " " + priceStat.toWriteExtended() + " ";}
			if (sell){ tempString=
				"SELL period:" + beginDate.toStringDateOnly() + "-"
						+ endDate.toStringDateOnly() + " " + priceStat.toWriteExtended() + " ";
			} 
			
		return tempString;
		}
		else return "";
	}
	
	
}
