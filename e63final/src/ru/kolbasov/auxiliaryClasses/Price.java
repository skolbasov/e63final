package ru.kolbasov.auxiliaryClasses;

public class Price implements Comparable<Price> {
	private Double highPrice;
	private Double lowPrice;
	private Double closePrice;

	public Price(Double highPrice, Double lowPrice, Double closePrice) {
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	@Override
	public String toString() {
		return "Price [highPrice=" + highPrice + ", lowPrice=" + lowPrice
				+ ", closePrice=" + closePrice + "]";
	}

	public Double getClosePrice() {
		return this.closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Price() {

		this.highPrice = 0d;
		this.lowPrice = 0d;
		this.closePrice = 0d;
	}

	public String toWriteSimple() {
		return this.highPrice + ";" + this.lowPrice + ";" + this.closePrice
				+ ";";
	}

	public String toWriteExtended() {
		return "Highest price: " + highPrice + " Lowest price: " + lowPrice
				+ " Close price:" + closePrice + " ";
	}

	@Override
	public int compareTo(Price o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareToUsingLowPrice(Price o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareToUsingHighPrice(Price o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
