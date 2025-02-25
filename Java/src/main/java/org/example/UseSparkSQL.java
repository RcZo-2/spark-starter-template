package org.example;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class UseSparkSQL {

    private static final Logger logger = LoggerFactory.getLogger(UseSparkSQL.class);

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().appName("Delta SQL Sample").getOrCreate();

        logger.info("---------- Spark session started ----------");

        Dataset<Row> data = spark.createDataFrame(
                Arrays.asList(
                        RowFactory.create(1, "value1"),
                        RowFactory.create(2, "value2")),
                new StructType(
                        new StructField[]{
                                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                                new StructField("value", DataTypes.StringType, false, Metadata.empty())
                        }
                )
        );

        data.write().format("delta").save("./tmp/delta-table");

        // create
        String deltaTablePath = "C:/XXX/spark-starter-template/tmp/delta-table";
        spark.sql("CREATE OR REPLACE TEMP VIEW delta_table AS SELECT * FROM delta.`" + deltaTablePath + "`");
        spark.sql("SELECT * FROM delta_table").show();

        // insert
        spark.sql("INSERT INTO delta.`" + deltaTablePath + "` VALUES (10,'v2')");
        spark.sql("SELECT * FROM delta_table").show();

        // update
        spark.sql("UPDATE delta.`" + deltaTablePath + "` SET value = 'new_value' WHERE id = 1");
        spark.sql("SELECT * FROM delta_table").show();

        // delete
        spark.sql("DELETE FROM delta.`" + deltaTablePath + "` WHERE id = 2");
        spark.sql("SELECT * FROM delta_table").show();
    }
}
