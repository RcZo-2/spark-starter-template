package org.example;


import org.apache.spark.sql.SparkSession
import org.example.config.AppConfig.load
import org.slf4j.LoggerFactory

object Main {

  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("Spark Scala Example")
      .getOrCreate()

    logger.info("---------- Spark session started ----------")
    val config = load("local")
    println(s"App Name: ${config.getString("app.name")}")

    spark.stop()
  }
}
