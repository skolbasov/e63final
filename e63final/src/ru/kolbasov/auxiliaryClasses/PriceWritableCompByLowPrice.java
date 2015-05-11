package ru.kolbasov.auxiliaryClasses;

import java.util.Comparator;

import ru.kolbasov.writables.PriceWritable;

public class PriceWritableCompByLowPrice implements Comparator<PriceWritable> {
	
    public int compare(PriceWritable price1, PriceWritable price2) {
     
    	return price1.getPrice().getLowPrice().compareTo(price2.getPrice().getLowPrice());
        
    }

}