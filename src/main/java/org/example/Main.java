package org.example;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;


public final class Main {
    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaSparkMongoTemplate")
                .config("spark.mongodb.read.connection.uri", "mongodb://XXX:27017/")
                .config("spark.mongodb.write.connection.uri", "mongodb://XXX:27017/")
                .getOrCreate();

        Dataset<Row> df = spark
                .read()
                .format("mongodb")
                .option("database", "spark")
                .option("collection", "test")
                .load();

        df.select("deviceId")
                .write()
                .format("mongodb")
                .mode("append")
                .option("database", "spark")
                .option("collection", "test2")
                .save();

        spark.stop();
    }
}