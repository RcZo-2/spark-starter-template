package org.example;


import io.delta.tables.DeltaTable;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Arrays;


public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {


        SparkSession spark = SparkSession.builder()
                .appName("Delta Sample")
                .getOrCreate();

        logger.info("---------- Spark session started ----------");

        String[] columns = {"name", "age"};
        Row[] data = {
                RowFactory.create("John", 28),
                RowFactory.create("Bob", 30),
                RowFactory.create("Alice", 25)
        };
        Dataset<Row> df = spark.createDataFrame(Arrays.asList(data), StructType.fromDDL("name STRING, age INT"));
        df = df.withColumn("data_date", functions.to_date(functions.lit("2025-02-17"), "yyyy-MM-dd"));
        df = df.repartition(1);

        String localTablePath = "./tmp/test_delta_table";

        spark.sql("SELECT current_database()").show();

        df.write().format("delta").mode("overwrite").save(localTablePath);

        spark.sql("CREATE TABLE IF NOT EXISTS test_delta_table (name STRING, age INT, data_date DATE) USING DELTA LOCATION '" + localTablePath + "'");

        spark.sql("SHOW TABLES").show();

        // Select
        DeltaTable delta_obj = DeltaTable.forPath(spark, localTablePath);
        Dataset<Row> delta_df2 = delta_obj.toDF();
        delta_df2.show(10);

        // Insert
        Row[] newdata = {
                RowFactory.create("Frank", 17),
                RowFactory.create("Tim", 18),
                RowFactory.create("James", 19)
        };
        Dataset<Row> append_df = spark.createDataFrame(Arrays.asList(newdata), StructType.fromDDL("name STRING, age INT"));
        append_df.write().format("delta").mode("append").save(localTablePath);

        // Select
        DeltaTable delta_obj2 = DeltaTable.forPath(spark, localTablePath);
        Dataset<Row> delta_df3 = delta_obj2.toDF();
        delta_df3.show(10);

        // Update
        delta_obj2.updateExpr(
                "name = 'Frank'",
                new HashMap<String, String>() {{
                    put("age", "'99'");
                }}
        );

        // Select
        DeltaTable delta_obj3 = DeltaTable.forPath(spark, localTablePath);
        Dataset<Row> delta_df4 = delta_obj3.toDF();
        delta_df4.show(10);

        // Delete
        delta_obj3.delete("age < 20");

        // Select
        DeltaTable delta_obj4 = DeltaTable.forPath(spark, localTablePath);
        Dataset<Row> delta_df5 = delta_obj4.toDF();
        delta_df5.show(10);

        // Upsert
        // check https://docs.delta.io/latest/delta-update.html#language-java

        spark.stop();
    }
}
