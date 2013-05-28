package com.reducer;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class StockReducer  extends TableReducer<Text, Text, ImmutableBytesWritable> {
	String columns = "price:open,price:high,price:low,price:close";
	String[] columnList = columns.split(",");
	String[] columnsValue=null;
	 Context con =null;
	 @Override
	 public void reduce(Text key, Iterable<Text> values, Context context)
             throws IOException, InterruptedException {
		 con=context;
		 String stockId = key.toString().substring(0, 6);
			String date = key.toString().substring(6);
			byte[] row = Bytes.add(
					Bytes.toBytes(stockId),
					Bytes.toBytes(Long.parseLong(date)));
			System.out.println(Arrays.toString(row));
			 for (Text value : values) {
				 columnsValue = value.toString().split(",");	 
			 }
			if (columnsValue.length == 4) {
				for (int k = 0; k < 4; k++) {
					String[] columnQual = columnList[k].split(":");
					byte[] col = Bytes.toBytes(columnQual[0]);
					byte[] qaul = Bytes.toBytes(columnQual[1]);
					byte[] value =  Bytes.toBytes(Float.parseFloat(columnsValue[k]));
					putRow(row, col, qaul, value);
				}
			}
	 }
	 public void putRow(byte[] row, byte[] column, byte[] qaul, byte[] value)
			 throws IOException, InterruptedException  {
			Put put = new Put(row);
			put.add(column, qaul, value);
			 con.write(new ImmutableBytesWritable(Bytes.toBytes("stockDataComposite")), put);
		}
}
