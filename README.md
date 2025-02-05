# 在本機開發 Apache Spark 以及試跑 server submit job

2025/2/5 更新初版

## 1. 事先準備

在開始之前，請確定你有下列項目:
- **[Java](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)**: 這裡用 AWS corretto 17，Openjdk、Zulu、Temurin 都可以
- **[IntelliJ](https://www.jetbrains.com/idea/download/)**: 或用 eclipse 方便你寫 java 的 IDE 都好


## 2. 準備 Spark submit Local Mode 環境

這邊是介紹如何跑 Spark submit Local Mode 的環境配置，cluster mode 要安裝步驟較多以後再補充，如要看開發環境相關可跳過

### 2-1. 配置步驟
| Step     | 項目                                                             | Memo                                   |
|----------|------------------------------------------------------------------|----------------------------------------|
| 1        | 到[官網](https://archive.apache.org/dist/spark/)下載對應的版本     | 一般都是下載 `spark-3.X.X-bin-hadoop3.tgz` |
| 2        | 解壓整個資料夾放到你電腦上一個簡潔的位置 (路徑不要太長)               | e.g. `D:\spark-3.5.1-bin-hadoop3`         |
| 3        | 下載 file system 模擬器 [winutils](https://github.com/cdarlint/winutils/tree/master) ，對應的 hadoop 版本看上面資料夾內 `RELEASE` 檔裡面有寫  | windows 才需要做這步，Mac、Linux 的童鞋可以跳 step 5    |
| 4        | 下載的東西放到你電腦上一個簡潔的位置 (路徑不要太長)               | e.g. `D:\winutils\hadoop-3.3.4`             |
| 5        | 設定執行檔參數，在 `spark-3.X.X-bin-hadoop3/bin/spark-submit.cmd` 第二行 @echo off 下面插入:
             <br>`set JAVA_HOME=C:\...\你java的安裝路徑` 指到你的 java 安裝路徑
             <br>`set HADOOP_HOME=D:\winutils\hadoop-3.3.4` 指到你step 4 放置的路徑
             <br>Mac、Linux的話 `spark-3.X.X-bin-hadoop3/bin/spark-submit` 在Licence註解區塊下面插入:
             <br>`export JAVA_HOME=/usr/lib/.../你java的安裝路徑`                                                         | 這些參數設在電腦的環境變數也可以，但我常 Spark+Java 多版本組合切換，習慣這樣子去設定好獨立環境 |
|6         |測試一下 shell```./spark-submit --master local[*] --class org.apache.spark.examples.JavaSparkPi ../examples/jars/spark-examples_2.12-3.5.1.jar```
            <br> `./spark-submit --master local[*] --class org.apache.spark.examples.JavaSparkPi ../examples/jars/spark-examples_2.12-3.5.1.jar`

### 2-2. 資料夾概述
test

## 3. Configuration (Optional)

You may need to configure additional settings depending on the project.

| Configuration     | Description                                      | Example Value    |
|-------------------|--------------------------------------------------|------------------|
| **Database URL**   | URL for connecting to your local database        | `localhost:5432` |
| **API Key**        | Key for accessing third-party APIs               | `1234ABCD5678`   |
| **Environment**    | Specify development or production environment   | `development`    |

## 4. Common Issues and Troubleshooting

### Issue 1: "Error: Port already in use"
- **Solution**: Kill the process using the port.
    - On macOS/Linux: 
      ```shell
      lsof -i :3000
      kill -9 <PID>
      ```
    - On Windows: Use Task Manager to find and kill the process.

### Issue 2: "Missing dependencies"
- **Solution**: Run the following command to install all required dependencies:
    ```shell
    npm install
    ```

## 5. Project Diagram

![Development Environment Diagram](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/bobo.png)

This diagram shows how the different components of the development environment communicate with each other.

## 6. Conclusion

Now you should be all set up with a fully functioning local development environment!

If you run into any issues, please refer to the troubleshooting section or open an issue in the repository.

---

> **Note:** This tutorial is based on a Node.js environment. Adjust steps for different technology stacks as necessary.
