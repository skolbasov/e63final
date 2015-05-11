package ru.kolbasov.auxiliaryClasses;

import java.util.Comparator;

import ru.kolbasov.writables.PriceWritable;

public class PriceWritableCompByDate implements Comparator<PriceWritable> {

	@Override
	public int compare(PriceWritable price1, PriceWritable price2) {

		return price1.getTimeslot().getTime()
				.compareTo(price2.getTimeslot().getTime());

	}

}
