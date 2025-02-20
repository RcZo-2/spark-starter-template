# Minio 相關操作

總之就是開源 Object Storage ，作為 Delta Lake Table 的載體

## 1. Docker 啟動

以下初始帳號密碼 (MINIO_ACCESS_KEY, MINIO_SECRET_KEY) 及 volume 掛載點 (-v 部分) 再依個人喜好設定

```shell
docker run  -p 9000:9000 -p 9090:9090 --name minio \
 -d --restart=always \
 -e MINIO_ACCESS_KEY=minio \
 -e MINIO_SECRET_KEY=minio@123 \
 -v ./data:/data \
 -v ./config:/root/.minio \
  minio/minio server /data  --console-address ":9000" --address ":9090"
```

到 9000 port 會看到管理站台，輸入上面設定的帳號密碼即可登入
![minioLogin](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/minioLogin.png) 

## 2. 配置基本使用相關設定

### 2-1. Bucket
進到管理站台，左邊 Buckets -> Create Bucket -> 然後取名 test 建立 (也可以取別的名字，對應你 Spark 程式路徑就好)
![minioAminioBucketccess](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/minioBucket.png)

### 2-2. Access Keys
管理站台左邊 Access Keys -> Create access key -> Create 然後 Copy 走 Access Key 跟 Secret Key 之後會用到，掉了或忘記也沒差再來這裡 create 一組就好
![minioAccess](https://raw.githubusercontent.com/RcZo-2/spark-starter-template/refs/heads/main/assets/images/minioAccess.png)


