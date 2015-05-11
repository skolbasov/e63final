package ru.kolbasov.auxiliaryClasses;

public class Variables {
	public static final String _DELIM = "__";
	// 30 calendar days period is close to 20
	// trading days period
	public static final Long _ANALYSISFRAME = 30L;
	public static final Long _DAYINMILLIS = 86400000L;
	public static final int _DAYSFORTURNOVER = 180;// during this amount of
													// calendar days the
													// strategy counts
													// successful
	// TODO add percent increase for turnover
}
