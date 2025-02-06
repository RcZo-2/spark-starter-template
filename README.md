# 在本機開發 Apache Spark 以及試跑 server submit job

2025/2/5 更新初版

## 1. 事先準備

在開始之前，請確定你有下列項目:
- [**Java**](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html): 這裡示範用 AWS corretto 17，[Openjdk、Zulu、Temurin] [8、11] 都可以
- [**IntelliJ**](https://www.jetbrains.com/idea/download/): 或用 eclipse 方便你寫 java 的 IDE 都好

## 2. 準備 Spark submit Local Mode 環境

這邊是介紹如何跑 Spark submit Local Mode 的環境配置，cluster mode 要安裝步驟較多以後再補充，如要看開發環境相關可直接跳 3.

### 2-1. 配置步驟

| **Step** | **項目** | **Memo** |
| --- | ------ | --- |
| 1 | 到[官網](https://archive.apache.org/dist/spark/)下載對應的版本 | 一般都是下載 `spark-3.X.X-bin-hadoop3.tgz` |
| 2 | 解壓整個資料夾放到你電腦上一個簡潔的位置 (路徑不要太長) | e.g. `D:\spark-3.5.1-bin-hadoop3` |
| 3 | 下載 file system 模擬器 [winutils](https://github.com/cdarlint/winutils/tree/master) ，對應的 hadoop 版本看上面資料夾內的 RELEASE 檔裡面有寫。windows 才需要做這步，Mac、Linux 的童鞋可以直接跳 step 6 | 沒有 hadoop 3.3.4 可以先用 3.3.5 |
| 4 | 下載的東西放到你電腦上一個簡潔的位置 (路徑不要太長) | e.g. `D:\winutils\hadoop-3.3.5` |
| 5 | rename spark-3.X.X-bin-hadoop3/conf/log4j2.properties.template 變成 log4j2.properties，然後在這個檔案裡加入以下設定:<br>`logger.shutdownhookmanager.name = org.apache.spark.util.ShutdownHookManager`<br>`logger.shutdownhookmanager.level = OFF`<br>`logger.sparkenv.name = org.apache.spark.SparkEnv`<br>`logger.sparkenv.level = ERROR` |winutils 老毛病，後續跑程式會有 temp 檔移不掉的錯誤，只能加這個把它 ignore 掉，可憐|
| 6 | 設定執行檔參數:<br>**windows** 在 spark-3.X.X-bin-hadoop3/bin/spark-submit.cmd 的第二行 @echo off 下面插入:<br>`set JAVA_HOME=C:\...\你java的安裝路徑`<br>`set HADOOP_HOME=D:\...\你step 4 放置的路徑`<br><br>**Mac、Linux** 在 spark-3.X.X-bin-hadoop3/bin/spark-submit 的Licence 註解區塊下面插入:<br>`export JAVA_HOME=/usr/lib/.../你java的安裝路徑` | 這些參數設在電腦的環境變數也可以，但我常 Spark+Java 多版本組合切換，習慣這樣子去設定可以獨立環境作業<br> <br> 插入參數示意: ![addenv](https://github.com/RcZo-2/spark-starter-template/blob/main/assets/images/addenv.png) |
| 7 | 測試配置結果:<br>**windows** 執行 `.\bin\spark-submit --master local[*] --class org.apache.spark.examples.JavaSparkPi ..\examples\jars\spark-examples_2.12-3.5.1.jar`<br><br>**Mac、Linux** 執行 `./bin/spark-submit --master local[*] --class org.apache.spark.examples.JavaSparkPi ../examples/jars/spark-examples_2.12-3.5.1.jar` | 配置正確的話，理論上會跑一堆 INFO log 之後看到一行算出來 *Pi is roughly 3……* ![runPi](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/runPi.png)

### 2-2. 資料夾概述

介紹幾個常用檔案跟放置路徑

    ./spark-3.X.X-bin-hadoop3
    ├── /bin                                    # client tool 相關啟動檔放這
    │   ├── spark-shell                         # scala 的 REPL
    │   ├── spark-sql                           # sql 的 REPL
    │   └── spark-submit                        # 也就是本次使用的 submit 工具
    ├── /conf                                   # 設定檔相關放這
    │   ├── spark-defaults.conf                 # 跑 Spark 相關設定可以登錄在這
    │   └── log4j2.properties                   # log 相關設定登錄在這
    ├── /data                                   # 配合範例程式的mock data
    ├── /examples                               # 範例程式
    │   ├── /jars                               # 範例程式jar
    │   │   └── spark-examples_2.12-3.X.X.jar   # 就是上面我們跑測試的jar
    │   └── /src                                # 範例程式原始碼
    ├── /jars                                   # 裡面就一堆 Spark 的依賴 jar 檔
    ├── /kubernetes                             # k8s 相關打包 image 的素材
    ├── /sbin                                   # cluster server tool 相關啟動檔放這
    └── ...

## 3. IDE 相關配置

直接使用本 repo 的範本

| **Step** | **項目** | **Memo** |
| --- | --- | --- |
| 1 |準備好開發工具，放好程式跟 pom 檔|務必確認 Java 版本和 Spark 版本要和上面一樣|
| 2 |設定 Run Config 第一組:<br>• **VM options** 設定 `-Dspark.master=local[*] -Dspark.driver.memory=1g -Dspark.executor.memory=1g -Dspark.log.level=WARN --add-exportsjava.base/sun.nio.ch=ALL-UNNAMED`<br>• **Environment variables** 加入 `HADOOP_HOME=..\上面 2-1 winutilS 路徑`<br>• **Modify options** 加入 「Add dependencies with provided scope to classpath」|eclipse 作法待補|
| 3 |IDE 試跑||
| 4 |設定 Run Config 第二組: ||
| 5 |到 spark-3.X.X-bin-hadoop3/conf 設定 spark-defaults.conf ，轉換 step 2 的 -Dspark 相關設定如下格式:<br>![SparkSubmitConf](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/SparkSubmitConf.png)||
| 6 |打包 jar ||
| 7 |Spark submit 試跑 ||

## 4. 常見問題與排障
紀錄一下大家碰到的狀況

## 5. 其他知識加值區
一些細節知識跟架構面的觀念建立

## 6. 小結
讀完大概是這樣

![cat](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/buffering-loading.gif)


---

> Note: 僅適用大樹中台數據科
>
