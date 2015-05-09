package ru.kolbasov;

public class Price {
	private Double highPrice;
	private Double lowPrice;
	public Price(Double highPrice, Double lowPrice, Double closePrice) {
		super();
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
	}
	private Double closePrice;
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
		return closePrice;
	}
	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}
	public Price() {
		// TODO Auto-generated constructor stub
	}

}
