package ru.kolbasov;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import ru.kolbasov.MRClasses.CorrelationMapper;
import ru.kolbasov.MRClasses.CorrelationReducer;
import ru.kolbasov.MRClasses.DayStatisticsMapper;
import ru.kolbasov.MRClasses.DayStatisticsReducer;
import ru.kolbasov.MRClasses.JoinStatisticsMapper;
import ru.kolbasov.MRClasses.JoinStatisticsReducer;
import ru.kolbasov.MRClasses.StrategyCheckMapper;
import ru.kolbasov.MRClasses.StrategyCheckReducer;
import ru.kolbasov.MRClasses.StrategyCheckResultsMapper;
import ru.kolbasov.MRClasses.StrategyCheckResultsReducer;
import ru.kolbasov.MRClasses.TickerStatMapper;
import ru.kolbasov.MRClasses.TickerStatReducer;
import ru.kolbasov.writables.CorrelationWritable;
import ru.kolbasov.writables.FullTickerStatWritable;
import ru.kolbasov.writables.PriceWritable;
import ru.kolbasov.writables.TickerStatWritable;

//TODO: add tickers dictionary for map
public class e63final {

	private static Job statisticsJob(Configuration conf, Path in, Path out)
			throws IOException {

		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "statisticsJob");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("statisticsJob");
		job.setMapperClass(TickerStatMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);
		job.setReducerClass(TickerStatReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FullTickerStatWritable.class);
		return job;
	}

	private static Job joinStatisticsJob(Configuration conf, Path in1,
			Path in2, Path out) throws IOException {

		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "joinStatisticsJob");

		MultipleInputs.addInputPath(job, in1, TextInputFormat.class);
		MultipleInputs.addInputPath(job, in2, TextInputFormat.class);
		
		FileOutputFormat.setOutputPath(job, out);
		job.setMapperClass(JoinStatisticsMapper.class);
		job.setJobName("joinStatisticsJob");
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(JoinStatisticsReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
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

	/**
	 * This job checks the strategy from the book Technical Analysis of the
	 * Futures Markets: A Comprehensive Guide to Trading Methods and
	 * Applications "
	 * 
	 * @param conf
	 *            - job configuration
	 * @param in
	 *            - directory with input files
	 * @param out
	 *            - directory with output files
	 * @return
	 * @throws IOException
	 */

	private static Job strategyCheckJob(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "strategyCheckJob");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("strategyCheckJob");
		job.setMapperClass(StrategyCheckMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);
		job.setReducerClass(StrategyCheckReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		return job;
	}

	private static Job strategyCheckResultsJob(Configuration conf, Path in,
			Path out) throws IOException {

		Job job = new Job(conf, "strategyCheckResultsJob");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("strategyCheckResultsJob");
		job.setMapperClass(StrategyCheckResultsMapper.class);
		job.setJarByClass(e63final.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setReducerClass(StrategyCheckResultsReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		return job;
	}

	/**
	 * This job should be run first. It makes first calculations and mappings
	 * for future StrategyCheckJob
	 * 
	 * @param conf
	 *            - job configuration
	 * @param in
	 *            - directory with input files
	 * @param out
	 *            - directory with output files
	 * @return
	 * @throws IOException
	 */
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

		FileUtils.deleteDirectory(new File(otherArgs[1]));
		FileUtils.deleteDirectory(new File(otherArgs[2]));
		Path temp1 = new Path(args[2] + "/1");
		Path temp2 = new Path(args[2] + "/2");
		Path temp3 = new Path(args[2] + "/3");
		Path out1 = new Path(args[1] + "/1");
		Path out2 = new Path(args[1] + "/2");
		if (otherArgs.length != 3) {
			System.err
					.println("Input, output and temp directories should be specified as input parameters");
			System.exit(2);
		}

		Job job = dayStatisticsJob(conf, in, temp1);
		job.waitForCompletion(true);
		Job job2 = strategyCheckJob(conf, temp1, temp2);
		job2.waitForCompletion(true);
		Job job3 = strategyCheckResultsJob(conf, temp2, out1);
		Job job4 = statisticsJob(conf, in, temp3);
		job3.waitForCompletion(true);
		job4.waitForCompletion(true);
		Job job5 = joinStatisticsJob(conf, temp3, temp2, out2);
	
		job5.waitForCompletion(true);
		// System.exit(job3.waitForCompletion(true) ? 0 : 1);
		System.exit(1);
	}
}
