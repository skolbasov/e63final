package ru.kolbasov;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
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


	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		FileUtils.deleteDirectory(new File(otherArgs[1]));

		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}

		Job job = new Job(conf, "word count");
		
		job.setJarByClass(e63final.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(PriceWritable.class);
		//job.setCombinerClass(TickerStatReducer.class);
		job.setReducerClass(TickerStatReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TickerStat.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
