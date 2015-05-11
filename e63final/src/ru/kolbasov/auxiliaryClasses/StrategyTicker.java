package ru.kolbasov.auxiliaryClasses;

import java.util.ArrayList;

import ru.kolbasov.writables.PriceWritable;

public class StrategyTicker {

	private StockDate beginDate;
	private Boolean buy = false;
	private StockDate endDate;
	private Price priceStat = new Price();
	private Boolean sell = false;

	public StrategyTicker(ArrayList<PriceWritable> values) {
		// TODO Auto-generated constructor stub
		if (values.size()>0){
			beginDate = values.get(0).getTimeslot();
		
		endDate = values.get(values.size() - 1).getTimeslot();
		for (PriceWritable price : values) {
			if (price.getPrice().getHighPrice() > priceStat.getHighPrice()) {
				priceStat.setHighPrice(price.getPrice().getHighPrice());
			}
			if (price.getPrice().getLowPrice() < priceStat.getLowPrice()) {
				priceStat.setLowPrice(price.getPrice().getLowPrice());
			}
		}
		priceStat.setClosePrice(values.get(values.size() - 1).getPrice()
				.getClosePrice());
		if (priceStat.getClosePrice() > priceStat.getHighPrice()) {
			this.buy = true;
		} else
			this.sell = true;
		}
	}

	@Override
	public String toString() {
		return "StrategyTicker [beginDate=" + beginDate + ", endDate="
				+ endDate + ", priceStat=" + priceStat + ", buy=" + buy
				+ ", sell=" + sell + "]";
	}

}
