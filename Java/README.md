# 前置準備

1.如果你是 windows 請放置 hadoop.dll 在專案路徑 [下載點](https://github.com/cdarlint/winutils/tree/master/hadoop-3.2.2/bin)

2.配置 ide 的 vm options 如下
```
-Dspark.master=local[*]
-Dspark.driver.memory=1g
-Dspark.executor.memory=1g
-Dspark.default.parallelism=4
-Dspark.sql.shuffle.partitions=1
-Dspark.sql.extensions=io.delta.sql.DeltaSparkSessionExtension
-Dspark.sql.catalog.spark_catalog=org.apache.spark.sql.delta.catalog.DeltaCatalog
-Dspark.delta.logStore.class=org.apache.spark.sql.delta.storage.S3SingleDriverLogStore
-Dspark.log.level=WARN
--add-exports
java.base/sun.nio.ch=ALL-UNNAMED
```

