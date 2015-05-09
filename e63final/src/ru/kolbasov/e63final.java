package ru.kolbasov;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import edu.hu.bigdata.HW06P3.MapClass1;
import edu.hu.bigdata.HW06P3.MapClass2;
import edu.hu.bigdata.HW06P3.Reduce1;
import edu.hu.bigdata.HW06P3.Reduce2;
//TODO: add tickers dictionary for map
public class e63final {

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, PriceWritable> {

		@Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

					String[] itr = value.toString().split(";");
			//TODO: add data check
			
			//System.out.println(itr[5]+" "+itr[6]+" "+itr[7]);
		//	itr[5].
		//	if (itr[5].matches("//[0-9,]")
			//		&& itr[6].matches("//[0-9,]")
				//	&& itr[7].matches("//[0-9,]")) {
				//System.out.println(itr[7]);
				String ticker = itr[0];

				PriceWritable price = new PriceWritable(itr[5], itr[6], itr[7],
						itr[2], itr[3]);
			//	System.out.println(price);
				//int volume = Integer.parseInt(itr[8]);

				context.write(new Text(ticker), price);

		//	}
		}
	}

	public static class TickerStatReducer extends
			Reducer<Text, PriceWritable, Text, TickerStat> {

		public void reduce(Text key, Iterable<PriceWritable> values,
				Context context) throws IOException, InterruptedException {
			TickerStat ticker=new TickerStat(values);
			context.write(key, ticker);
		}

	}

	private static Job createJob1(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "job1");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("job1");
		job.setMapperClass(TokenizerMapper.class);
		job.setJarByClass(e63final.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);

		job.setReducerClass(TickerStatReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TickerStat.class);
		return job;
	}

	private static Job createJob2(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = new Job(conf, "job2");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("job2");
		job.setMapperClass(TokenizerMapper.class);
		job.setJarByClass(e63final.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);

		job.setReducerClass(TickerStatReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TickerStat.class);
		return job;
	}
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		Path temp = new Path("chain-temp");
		FileUtils.deleteDirectory(new File(otherArgs[1]));

		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}

		Job job1 = createJob1(conf, in, temp);
		job1.waitForCompletion(true);
		Job job2 = createJob2(conf, in, temp);


		System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}
}
