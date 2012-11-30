package org.apache.solr.hadoop.tika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.apache.solr.hadoop.LineRandomizerMapper;
import org.apache.solr.hadoop.LineRandomizerReducer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LineRandomizerMapperReducerTest extends Assert {

  private MapDriver<LongWritable, Text, LongWritable, Text> mapDriver;
  private ReduceDriver<LongWritable, Text, Text, NullWritable> reduceDriver;
  private MapReduceDriver<LongWritable, Text, LongWritable, Text, Text, NullWritable> mapReduceDriver;

  @Before
  public void setUp() {
    LineRandomizerMapper mapper = new LineRandomizerMapper();
    LineRandomizerReducer reducer = new LineRandomizerReducer();
    mapDriver = MapDriver.newMapDriver(mapper);
    reduceDriver = ReduceDriver.newReduceDriver(reducer);
    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
  }

  @Test
  public void testMapReduce1Item() throws IOException {
    mapReduceDriver.withInput(new LongWritable(0), new Text("hello"));
    mapReduceDriver.withOutput(new Text("hello"), NullWritable.get());
    mapReduceDriver.runTest();
  }
  
  @Test
  public void testMapReduce2Items() throws IOException {
    mapReduceDriver.withAll(Arrays.asList(
        new Pair<LongWritable, Text>(new LongWritable(0), new Text("hello")),
        new Pair<LongWritable, Text>(new LongWritable(1), new Text("world"))
        ));
    mapReduceDriver.withAllOutput(Arrays.asList(
        new Pair<Text, NullWritable>(new Text("world"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("hello"), NullWritable.get())
        ));
    mapReduceDriver.runTest();
  }
  
  @Test
  public void testMapReduce3Items() throws IOException {
    mapReduceDriver.withAll(Arrays.asList(
        new Pair<LongWritable, Text>(new LongWritable(0), new Text("hello")),
        new Pair<LongWritable, Text>(new LongWritable(1), new Text("world")),
        new Pair<LongWritable, Text>(new LongWritable(2), new Text("nadja"))
        ));
    mapReduceDriver.withAllOutput(Arrays.asList(
        new Pair<Text, NullWritable>(new Text("world"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("hello"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("nadja"), NullWritable.get())
        ));
    mapReduceDriver.runTest();
  }
  
  @Test
  public void testMapReduce4Items() throws IOException {
    mapReduceDriver.withAll(Arrays.asList(
        new Pair<LongWritable, Text>(new LongWritable(0), new Text("hello")),
        new Pair<LongWritable, Text>(new LongWritable(1), new Text("world")),
        new Pair<LongWritable, Text>(new LongWritable(2), new Text("nadja")),
        new Pair<LongWritable, Text>(new LongWritable(3), new Text("basti"))
        ));
    mapReduceDriver.withAllOutput(Arrays.asList(
        new Pair<Text, NullWritable>(new Text("world"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("basti"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("hello"), NullWritable.get()),
        new Pair<Text, NullWritable>(new Text("nadja"), NullWritable.get())
        ));
    mapReduceDriver.runTest();
  }
  
}