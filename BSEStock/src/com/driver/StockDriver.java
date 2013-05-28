package com.driver;

import com.mapper.StockMapper;
import com.reducer.StockReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StockDriver extends Configured implements Tool {
	private final String inputFile = "hdfs://localhost:54310/user/hduser/projects/input/bsestock/BSEStockData.txt";
	@Override
	public int run(String[] args) throws Exception {
		Configuration config = HBaseConfiguration.create();
		Job job = new Job(config,"BSEStock");
		FileInputFormat.addInputPath(job, new Path(inputFile));
		job.setJarByClass(StockDriver.class);
		job.setMapperClass(StockMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
		TableMapReduceUtil.initTableReducerJob("stockDataComposite", StockReducer.class, job);
        //job.setOutputFormatClass(MultiTableOutputFormat.class);
        //job.setNumReduceTasks(1);
       // job.setReducerClass(StockReducer.class);
		return (job.waitForCompletion(true) ? 1 : 0);

	}

	public static void main(String[] args) {

		try {
			int exitcode = ToolRunner.run(new StockDriver(), args);
			System.exit(exitcode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
