package ru.kolbasov;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

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

  public static class TokenizerMapper 
       extends Mapper<Object, Text, String, PriceWritable>{
    
    private IntWritable quantity = new IntWritable();
    private Integer quantityInt;
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
     // StringTokenizer itr = new StringTokenizer(value.toString(),";");
      //System.out.println(itr.countTokens());
    	String val=value.toString().replace(".", ",");//replacing . to , in prices for correct convertation to double
      
    	String[] itr=val.split(";");
    	if (itr[5].matches("//[0-9],[0-9]")&&itr[6].matches("//[0-9],[0-9]")&&itr[7].matches("//[0-9],[0-9]")&&itr[8].matches("//[0-9],[0-9]")){
    	System.out.println(itr[7]);
      String ticker=itr[0];
   
      PriceWritable price=new PriceWritable(itr[5],itr[6],itr[7],itr[2],itr[3]);

      int volume=Integer.parseInt(itr[8]);
      
    	
      context.write(ticker, price);
    	
    	
    	}
    	
     // System.out.println("----"+value.toString()+"----");
     
      //reading key-value pairs from the file. i know exactly that each string contain two values, one is key, another is value
      
 /*   if (itr.countTokens()==2){ 
        word.set(itr.nextToken());
        quantityInt=Integer.parseInt(itr.nextToken());
        quantity.set(quantityInt);
        String wrd;
      wrd=word.toString();
      wrd.trim();
     wrd=wrd.toLowerCase();//in order to provide case undependable results
   //not sure how to create a single regular expression, will do it couple of times - for different ascii codes populations. It is not very efficient, but for this task is ok
     wrd=wrd.replaceAll("[!-\\/]","");
     wrd=wrd.replaceAll("[:-@]","");
     wrd=wrd.replaceAll("[\\-']", "");
    // wrd=wrd.replaceAll("[\\-'", "");
    wrd=wrd.replaceAll("\\[", ""); 
     wrd=wrd.replaceAll("[{-~]", ""); 
       word.set(wrd);
*/
        
  //  }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,Text,Text,Text> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
     /*
    	result.set(0);
      
      //if (!key.toString().matches("[0-9]{8}")){//if string is not like 01:004:010 without semicolons then leave it  
    	  
      
    	  int sum = 0;
          for (IntWritable val : values) {
            sum += val.get();
          }
          if (sum<1000){//if sum of values is less than 1000 then let it stay after reduce, otherwise drop it
        	  result.set(sum); 
        
        	  
          context.write(new Text(result.toString()),key);
          }

    */
    }
    
    
    
  }

  public static void main(String[] args) throws Exception {
   
	  Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
  
    FileUtils.deleteDirectory(new File(otherArgs[1]));
    
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }

    Job job = new Job(conf, "word count");
    job.setJarByClass(e63final.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
