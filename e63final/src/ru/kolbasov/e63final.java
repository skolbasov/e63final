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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class e63final {

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, PriceWritable> {

		@Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {


		//String val = value.toString().replace(".", ",");// replacing . to ,
															// in prices for
															// correct
															// convertation to
															// double

			
		
			String[] itr = value.toString().split(";");
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

	public static class IntSumReducer extends
			Reducer<Text, Iterable<PriceWritable>, Text, PriceWritable> {
		// private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<PriceWritable> values,
				Context context) throws IOException, InterruptedException {
		
			
			for(PriceWritable next:values){
				System.out.println(next.toString());
				context.write(key, next);
			}
			/*
			 * result.set(0);
			 * 
			 * //if (!key.toString().matches("[0-9]{8}")){//if string is not
			 * like 01:004:010 without semicolons then leave it
			 * 
			 * 
			 * int sum = 0; for (IntWritable val : values) { sum += val.get(); }
			 * if (sum<1000){//if sum of values is less than 1000 then let it
			 * stay after reduce, otherwise drop it result.set(sum);
			 * 
			 * 
			 * context.write(new Text(result.toString()),key); }
			 */
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
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(PriceWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
