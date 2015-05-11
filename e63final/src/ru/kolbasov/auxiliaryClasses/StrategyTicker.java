package ru.kolbasov.auxiliaryClasses;

import java.util.ArrayList;
import java.util.Collections;

import ru.kolbasov.writables.PriceWritable;

public class StrategyTicker {

	private StockDate beginDate = new StockDate();
	private Boolean buy = false;
	private StockDate endDate = new StockDate();
	// TODO think about lowest price
	private Price priceStat = new Price(0d, 999999999999d, 0d);

	public StockDate getEndDate() {
		return endDate;
	}

	public StockDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(StockDate beginDate) {
		this.beginDate = beginDate;
	}

	private Boolean sell = false;
	private Boolean wait = false;
	private String provedExpl = "";

	public String getProvedExpl() {
		return provedExpl;
	}

	public void setProvedExpl(String provedExpl) {
		this.provedExpl = provedExpl;
	}

	public Boolean getBuy() {
		return buy;
	}

	public Price getPriceStat() {
		return priceStat;
	}

	public void setPriceStat(Price priceStat) {
		this.priceStat = priceStat;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}

	public Boolean getSell() {
		return sell;
	}

	public void setSell(Boolean sell) {
		this.sell = sell;
	}

	public Boolean getWait() {
		return wait;
	}

	public void setWait(Boolean wait) {
		this.wait = wait;
	}

	private Boolean proved = false;

	public Boolean getProved() {
		return proved;
	}

	public void setProved(Boolean proved) {
		this.proved = proved;
	}

	public StrategyTicker(ArrayList<PriceWritable> values) {
		// TODO Auto-generated constructor stub
		if (values.size() > 0) {
			beginDate = values.get(0).getTimeslot();

			endDate = values.get(values.size() - 1).getTimeslot();

			priceStat.setHighPrice(Collections
					.max(values, new PriceWritableCompByHighestPrice()).getHighPrice());
			priceStat.setLowPrice(Collections
					.min(values, new PriceWritableCompByLowPrice()).getLowPrice());
			priceStat.setClosePrice(values.get(values.size() - 1).getClosePrice());
			if (values.get(values.size() - 1).getHighPrice() >= priceStat
					.getHighPrice()) {
				this.buy = true;
			} else if (values.get(values.size() - 1).getLowPrice() <= priceStat
					.getLowPrice())
				this.sell = true;
			else
				this.wait = true;
		}
	}

	@Override
	public String toString() {
		return "StrategyTicker [beginDate=" + beginDate.toStringDateOnly()
				+ ", endDate=" + endDate.toStringDateOnly() + ", priceStat="
				+ priceStat + ", buy=" + buy + ", sell=" + sell + ", wait="
				+ wait + "]";
	}

	/**
	 * 
	 * @return formatted recommendation string
	 */
	public String toWrite() {
		String tempString = "";
		if (!this.wait) {
			if (buy) {

				tempString = "BUY period:" + beginDate.toStringDateOnly() + "-"
						+ endDate.toStringDateOnly() + " "
						+ priceStat.toWriteExtended() + Variables._DELIM
						+ "Proved:" + proved + Variables._DELIM;
			}
			if (sell) {
				tempString = "SELL period:" + beginDate.toStringDateOnly()
						+ "-" + endDate.toStringDateOnly() + " "
						+ priceStat.toWriteExtended() + Variables._DELIM
						+ "Proved:" + proved + Variables._DELIM;
			}
			if (proved) {

				tempString = tempString.concat(" explanation: " + provedExpl
						+ ". ");
			} else
				tempString = tempString.concat(" ");
			return tempString;
		} else
			return "";
	}

}
