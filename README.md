# 在本機開發 Apache Spark 以及試跑 server submit job

2025/2/5 更新初版

## 1. 事先準備

在開始之前，請確定你有下列項目:
- **[Java](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)**: 這裡用 AWS corretto 17，Openjdk、Zulu、Temurin 都可以
- **[IntelliJ](https://www.jetbrains.com/idea/download/)**: 或用 eclipse 方便你寫 java 都好


## 2. 配置 Spark submit Local Mode 環境

這邊是配製如何跑 Spark submit Local Mode 的環境準備，cluster mode 要安裝步驟較多以後再補充，如要看開發環境相關可跳過

| Step | 項目                                          | Memo                         |
|----------|------------------------------------------------------|----------------------------------------|
| 1        | 到[官網](https://archive.apache.org/dist/spark/)下載對應的版本                                | ```shell git clone https://github.com/your/repo.git ``` |
| 2        | Navigate to the project folder                      | ```shell cd repo-folder ```        |
| 3        | Install project dependencies (for Node.js example)  | ```shell npm install ```           |
| 4        | Start the development server                        | ```shell npm start ```             |
| 5        | Access the app in the browser                       | Open `http://localhost:3000` in your browser |

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
