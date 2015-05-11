package ru.kolbasov.writables;

//TODO: add logs using log4j
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import ru.kolbasov.auxiliaryClasses.PriceWritableCompByDate;
import ru.kolbasov.auxiliaryClasses.StrategyTicker;
import ru.kolbasov.auxiliaryClasses.Variables;

public class StrategyTickerStatWritable {

	DecimalFormat df = new DecimalFormat("#.######");

	private ArrayList<PriceWritable> pricesList = new ArrayList<PriceWritable>();
	private ArrayList<StrategyTicker> strategyRecomendations = new ArrayList<StrategyTicker>();

	public StrategyTickerStatWritable() {

	}

	public StrategyTickerStatWritable(Iterable<PriceWritable> prices) {

		for (PriceWritable price : prices) {
			PriceWritable pw = new PriceWritable(price);
			pricesList.add(pw);

		}
		// Arrange values by days
		Collections.sort(pricesList, new PriceWritableCompByDate());

		int iterator = -1;
		for (PriceWritable price : pricesList) {
			ArrayList<PriceWritable> strategyPrices = new ArrayList<PriceWritable>();
			iterator++;

			Long analysisFrameEnd = price.getTimeslotInLong()
					+ (Variables._DAYINMILLIS * Variables._ANALYSISFRAME);
			// Check whether the priceList has necessary amount of days for
			// analysis
			if (pricesList.get(pricesList.size() - 1).getTimeslotInLong() > analysisFrameEnd) {
				int i = 0;
				while (pricesList.get(iterator + i).getTimeslotInLong() < analysisFrameEnd) {
					strategyPrices.add(pricesList.get(i + iterator));
					i++;
				}
				strategyRecomendations.add(new StrategyTicker(strategyPrices));
				strategyPrices.clear();
			} else {
				//Do nothing
			}
		}

		prove(strategyRecomendations);
	}

	public ArrayList<StrategyTicker> getStrategyRecomendations() {
		return strategyRecomendations;
	}

	/**
	 * Proves the strategy
	 * 
	 * @param values
	 */
	public void prove(ArrayList<StrategyTicker> values) {
		int iterator = -1;
		for (StrategyTicker value : values) {
			iterator++;
			for (int i = 0; i < values.size(); i++) {
				if (value.getBuy()) {
					if ((i + iterator < values.size())
							&& (values.get(i + iterator).getBuy() && value
									.getPriceStat().getHighPrice() < values
									.get(i + iterator).getPriceStat()
									.getHighPrice())) {
						value.setProved(true);
						value.setProvedExpl("Future buy periods with higher prices exist");
						break;
					}
					for (PriceWritable tempPw : pricesList) {
						if (value.getEndDate().compareTo(tempPw.getTimeslot()) < 0) {
							// if below 0 - the tempPw value was later than
							// StrategyTicker value
							// TODO Only single he is definitely not enough. Add
							// some period for strict prove
							if ((value.getPriceStat().getHighPrice() < tempPw
									.getPrice().getHighPrice())
									|| (value.getPriceStat().getHighPrice() < tempPw
											.getPrice().getLowPrice())
									|| (value.getPriceStat().getHighPrice() < tempPw
											.getPrice().getClosePrice())) {
								value.setProved(true);
								value.setProvedExpl("Future period with higher prices exists:"
										+ tempPw);
								break;
							}
						}
					}
				}
				if (value.getSell()) {
					if ((i + iterator < values.size())
							&& (values.get(i + iterator).getSell() && value
									.getPriceStat().getLowPrice() < values
									.get(i + iterator).getPriceStat()
									.getLowPrice())) {
						value.setProved(true);
						value.setProvedExpl("Future sell periods with lower prices exist");
						break;
					}
					for (PriceWritable tempPw : pricesList) {
						if (value.getEndDate().compareTo(tempPw.getTimeslot()) < 0) {
							// if below 0 - the tempPw value was later than
							// StrategyTicker value
							// TODO Only single he is definitely not enough. Add
							// some period for strict prove
							if ((value.getPriceStat().getLowPrice() > tempPw
									.getPrice().getLowPrice())

									|| (value.getPriceStat().getHighPrice() > tempPw
											.getPrice().getClosePrice())) {
								value.setProved(true);
								value.setProvedExpl("Future period with lower prices exists:"
										+ tempPw);
								break;
							}
						}
					}
				}
				// TODO add check for wait
			}
		}

	}

	public void setStrategyRecomendations(
			ArrayList<StrategyTicker> strategyRecomendations) {
		this.strategyRecomendations = strategyRecomendations;
	}

	@Override
	public String toString() {
		return "StrategyTickerStatWritable [strategyRecomendations="
				+ strategyRecomendations + "]";
	}

	public String toWrite() {
		String tempString = "";

		for (StrategyTicker recomendation : this.strategyRecomendations) {
			if (!recomendation.toWrite().isEmpty()) {
				tempString = tempString.concat(recomendation.toWrite());

			}
		}
		if (tempString.isEmpty()) {
			return "";
		}

		else {
			return "AVAILABLE:".concat(tempString);
		}
	}

}
