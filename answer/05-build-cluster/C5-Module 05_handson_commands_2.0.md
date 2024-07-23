# Corda5トレーニング:Module05 コマンドテキスト

## ハンズオン環境

### トレーニング環境EC2に接続する

```bash
ssh -i aws-sshkey-corda-trg02.pem -p **sshポート** corda@**外部公開アドレス**
```

### Kubernetes のクラスタの切り替え

```bash
aws eks update-kubeconfig --name **eks-cluster** --region ap-northeast-2
```

### context(cluster) の確認

```bash
kubectl config current-context
```

### namespace の作成

```bash
kubectl create namespace **namespace**
```

### namespace の永続的切り替え

```bash
kubectl config set-context $(kubectl config current-context) --namespace=**namespace**
```

### namespace の切り替え確認

```bash
kubectl config view --minify | grep namespace:
```

---

## Corda Helm Chart

### Chartパラメータの確認方法(readme)

```bash
cd /home/corda/training
helm show readme corda-enterprise.tar
```

### Chartパラメータの確認方法(values)

```bash
helm show values corda-enterprise.tar
```

## Cluster構築

### Secretファイルの確認

```bash
cat secret.yaml
```

### Secret起動

```bash
kubectl apply -f secret.yaml
```

### Secret詳細情報確認

```bash
kubectl describe secret my-secret
```

### bitnami-kafka起動

```bash
helm install kafka kafka-18.5.0.tgz -f bitnami-kafka.yaml
```

### 結果を以下にコピー&ペーストしてください

例↓ (上書きしてしまって構いません。)

```bash
corda@ip-172-16-102-177:~/training$ helm install kafka kafka-18.5.0.tgz -f bitnami-kafka.yaml
NAME: kafka
LAST DEPLOYED: Mon Jun 24 04:34:07 2024
NAMESPACE: corda-cluster1
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: kafka
CHART VERSION: 18.5.0
APP VERSION: 3.2.3

** Please be patient while the chart is being deployed **

Kafka can be accessed by consumers via port 9092 on the following DNS name from within your cluster:

    kafka.corda-cluster1.svc.cluster.local

Each Kafka broker can be accessed by producers via port 9092 on the following DNS name(s) from within your cluster:

    kafka-0.kafka-headless.corda-cluster1.svc.cluster.local:9092
    kafka-1.kafka-headless.corda-cluster1.svc.cluster.local:9092
    kafka-2.kafka-headless.corda-cluster1.svc.cluster.local:9092

To create a pod that you can use as a Kafka client run the following commands:

    kubectl run kafka-client --restart='Never' --image docker.io/bitnami/kafka:3.2.3-debian-11-r1 --namespace corda-cluster1 --command -- sleep infinity
    kubectl exec --tty -i kafka-client --namespace corda-cluster1 -- bash

    PRODUCER:
        kafka-console-producer.sh \
            
            --broker-list kafka-0.kafka-headless.corda-cluster1.svc.cluster.local:9092,kafka-1.kafka-headless.corda-cluster1.svc.cluster.local:9092,kafka-2.kafka-headless.corda-cluster1.svc.cluster.local:9092 \
            --topic test

    CONSUMER:
        kafka-console-consumer.sh \
            
            --bootstrap-server kafka.corda-cluster1.svc.cluster.local:9092 \
            --topic test \
            --from-beginning
```

### bitnami-postgreSQL起動

```bash
helm install postgres postgresql-13.1.5.tgz -f bitnami-postgres.yaml
```

### 結果を以下にコピー&ペーストしてください

```bash
NAME: postgres
LAST DEPLOYED: Thu May 16 01:06:06 2024
NAMESPACE: corda-cluster1
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: postgresql
CHART VERSION: 13.1.5
APP VERSION: 16.0.0

** Please be patient while the chart is being deployed **

WARNING: PostgreSQL has been configured without authentication, this is not recommended for production environments.

PostgreSQL can be accessed via port 5432 on the following DNS names from within your cluster:

    postgres-postgresql.corda-cluster1.svc.cluster.local - Read/Write connection

To get the password for "postgres" run:

    export POSTGRES_ADMIN_PASSWORD=$(kubectl get secret --namespace corda-cluster1 my-secret -o jsonpath="{.data.postgrespassword}" | base64 -d)

To get the password for "user1" run:

    export POSTGRES_PASSWORD=$(kubectl get secret --namespace corda-cluster1 my-secret -o jsonpath="{.data.password}" | base64 -d)

To connect to your database run the following command:

    kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace corda-cluster1 --image docker.io/bitnami/postgresql:14.6.0-debian-11-r33 \
      --command -- psql --host postgres-postgresql -d cordacluster -p 5432

    > NOTE: If you access the container using bash, make sure that you execute "/opt/bitnami/scripts/postgresql/entrypoint.sh /bin/bash" in order to avoid the error "psql: local user with ID 1001} does not exist"

To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace corda-cluster1 svc/postgres-postgresql 5432:5432 &
    psql --host 127.0.0.1 -d cordacluster -p 5432

WARNING: The configured password will be ignored on new installation in case when previous PostgreSQL release was deleted through the helm command. In that case, old PVC will have an old password, and setting it through helm won't take effect. Deleting persistent volumes (PVs) will solve the issue.
```

### Pod が Running であることを確認する

```bash
kubectl get pods
```

### PostgreSQL アクセス確認

環境変数PGPASSWORDにmy-secretのpostgrespasswordのプレーン値を設定

```bash
export PGPASSWORD=$(kubectl get secret my-secret -o jsonpath="{.data.postgrespassword}" | base64 -d)
```

*メモした helm install bitnami-postgres の結果の中に "To connect to your database from outside the cluster execute the following commands:" と記載されている箇所があるので、探して2行のコマンドを実行してください*

上の例の場合↓

```bash
kubectl port-forward --namespace corda-cluster1 svc/postgres-postgresql 5432:5432 &
psql --host 127.0.0.1 -d cordacluster -p 5432
```

### PostgreSQL から抜ける

```bash
exit
```

### Kafka アクセス確認

* bitnami-kafka起動にて表示された出力の中に「To create a pod that you can use as a Kafka client run the following commands:」と記載されている箇所があるので、探して2行のコマンドを実行してください*

上の例の場合↓

```bash
kubectl port-forward --namespace corda-cluster1 svc/postgres-postgresql 5432:5432 &
psql --host 127.0.0.1 -d cordacluster -p 5432
```

上記が正常終了している場合、起動したkafka clientのdocker containerにログインできていますので、続けて以下のコマンドを実行します。

```bash
# 「corda-cluster1」は自分のnamespaceの名前に差し替えてください。
kafka-topics.sh --list --bootstrap-server kafka.corda-cluster1.svc.cluster.local:9092
```

---

## Cluster 起動

### vim で values.yaml を開く

```bash
vim values.yaml
```

### Cluster 実行

```bash
helm install corda corda-enterprise.tar -f ./values.yaml
```

### Pod の確認

```bash
kubectl get pods
```

---

## Cluster アクセス

### Webブラウザで Corda cluster にアクセス(REST APIコードのたびに標準出力がでるのが気になる場合は、もう一つターミナルを立ち上げて実行してください)

```bash
kubectl port-forward deployment/corda-corda-enterprise-rest-worker 8888 --address='0.0.0.0' &
```

### REST WORKER のログインユーザーのユーザーネーム取得

```bash
kubectl get secret my-secret -o go-template='{{ .data.restapiusername | base64decode }}'
```

### REST WORKER のログインユーザーのパスワード取得

```bash
kubectl get secret my-secret -o go-template='{{ .data.restapipassword | base64decode }}'
```

### 以下にユーザーネームとパスワードをメモしてください

```bash
username: 
password: 
```

### Webブラウザでアクセス

https://x.x.x.x:8888/api/v5_2/swagger

### logの確認(XXXXXXXXXX-XXXXXは get pods コマンドで取得したものを使用してください)

```bash
kubectl logs corda-corda-enterprise-rest-worker-XXXXXXXXXX-XXXXX
```

---

## 初期処理確認(「C5-Module 05 - 【参考資料】CLI 2.0.pptx」の実機処理確認の項で使用するコマンドです。ご興味あれば是非おためしください)

### Pod名の確認

```bash
kubectl get pods
```

### Podの詳細情報を取得(XXXXXは get pods コマンドで取得したものを使用してください)

```bash
kubectl describe pods corda-corda-enterprise-setup-db-XXXXX
```

### PostgreSQL に再度ログイン

```bash
psql -h localhost -d cordacluster
```

### テーブル確認

```bash
SELECT schemaname, tablename, tableowner FROM pg_tables;
```

### PostgreSQL から抜ける

```bash
exit
```
