package ru.kolbasov;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import ru.kolbasov.MRClasses.CorrelationMapper;
import ru.kolbasov.MRClasses.CorrelationReducer;
import ru.kolbasov.MRClasses.DayStatisticsMapper;
import ru.kolbasov.MRClasses.DayStatisticsReducer;
import ru.kolbasov.MRClasses.StrategyCheckMapper;
import ru.kolbasov.MRClasses.StrategyCheckReducer;
import ru.kolbasov.MRClasses.TickerStatMapper;
import ru.kolbasov.MRClasses.TickerStatReducer;
import ru.kolbasov.writables.CorrelationWritable;
import ru.kolbasov.writables.FullTickerStatWritable;
import ru.kolbasov.writables.PriceWritable;
import ru.kolbasov.writables.StrategyTickerStatWritable;
import ru.kolbasov.writables.TickerStatWritable;

//TODO: add tickers dictionary for map
public class e63final {

	private static Job createJob1(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "job1");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("job1");
		job.setMapperClass(TickerStatMapper.class);
		job.setJarByClass(e63final.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);

		job.setReducerClass(TickerStatReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FullTickerStatWritable.class);
		return job;
	}

	private static Job createJob2(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "job2");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("job2");
		job.setMapperClass(CorrelationMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(CorrelationWritable.class);
		job.setReducerClass(CorrelationReducer.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(TickerStatWritable.class);
		return job;
	}
//This job checks the strategy from the book "Technical Analysis of the Futures Markets: A Comprehensive Guide to Trading Methods and Applications "
	private static Job strategyCheckJob(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "job3");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("job3");
		job.setMapperClass(StrategyCheckMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);
		job.setReducerClass(StrategyCheckReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		return job;
	}
//This job should be completed prior to the StrategyCheckJob	
	private static Job dayStatisticsJob(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "DayStatisticsJob");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("DayStatisticsJob");
		job.setMapperClass(DayStatisticsMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);
		job.setReducerClass(DayStatisticsReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		return job;
	}
	
	
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		Path temp = new Path(args[2]);
		FileUtils.deleteDirectory(new File(otherArgs[1]));
		FileUtils.deleteDirectory(new File(otherArgs[2]));
		if (otherArgs.length != 3) {
			System.err
					.println("Input, output and temp directories should be specified as input parameters");
			System.exit(2);
		}

	//	Job job1 = createJob1(conf, in, out);
		//job1.waitForCompletion(true);
		Job job = dayStatisticsJob(conf, in, temp);
		job.waitForCompletion(true);
		Job job2=strategyCheckJob(conf,temp,out);

		System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}
}
