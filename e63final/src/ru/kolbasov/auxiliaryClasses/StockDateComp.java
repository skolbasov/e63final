package ru.kolbasov.auxiliaryClasses;

import java.util.Comparator;

public class StockDateComp implements Comparator<StockDate> {
	
    public int compare(StockDate date1, StockDate date2) {
        return date1.getDate().compareTo(date2.getDate());
    }

}
