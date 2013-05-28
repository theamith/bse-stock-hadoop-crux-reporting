package com.mapper;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class StockMapper  extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>{
	private Text mapperKey = new Text();
    private Text mapperValue = new Text();
	 public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	        String[] valueSplitted = value.toString().split(",");
	        if (valueSplitted.length == 11) {
	            String row = valueSplitted[0];
	            String openprice = valueSplitted[1];
	            String highprice = valueSplitted[2];
	            String lowprice = valueSplitted[3];
	            String closeprice = valueSplitted[4];

	            mapperKey.set(row);
	            mapperValue.set(openprice + "," + highprice+","+lowprice+","+closeprice);
	            //System.out.println("key===>>"+mapperKey+"Value===>>"+mapperValue);
	            context.write(mapperKey, mapperValue);
	        }
	    }
}
