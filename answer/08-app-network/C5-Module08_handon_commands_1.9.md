# Corda5トレーニング:Module08 コマンドテキスト
## 環境変数
```bash
cat ~/.profile 
```

---

## MGM登録 - MGM登録スクリプト実行
### ディレクトリ移動 & スクリプト実行権限確認

```bash
cd /home/corda/training/register_tool/scripts/main
ls -l
```

### MGM 登録スクリプト実行
```bash
./registerNewMGM.sh
CPIの名前をランダムに付与します。CPIの種類を番号で選択してください。1: node, 2: notary, 3: mgm >> # 「3」を選択してEnter
デフォルトで設定されているCPIバージョンは1.0.0.0-SNAPSHOTです。変更したい場合は、入力してください。そのままで良い場合は、未入力でEnterを押下してください。 # 未入力でEnter
仮想Node名を設定してください。各属性間の間は空白を設けないでください。設定例: C=GB,L=London,O=MGM >>  >> # 「C=GB,L=London,O=MGM」を入力してEnter
```

---

## CPI登録 - CPI登録スクリプト実行

### CPI 登録スクリプト実行
```bash
./registerNewCPI.sh
CPIを登録するMGMのHolding IDを指定してください。例: 9FF3CED70F62 >> # 先ほど作成したMGMのHolding IDを設定
CPIの名前をランダムに付与します。CPIの種類を番号で選択してください。1: node, 2: notary, 3: mgm >> # 「1」を選択してEnter
デフォルトで設定されているCPIバージョンは1.0.0.0-SNAPSHOTです。変更したい場合は、入力してください。そのままで良い場合は、未入力でEnterを押下してください。 # 未入力でEnter
1. notary-plugin-non-validating-server-5.2.1.0-package.cpb
2. workflows-1.0-SNAPSHOT-package.cpb
使用するCPBファイルを番号で選択してください。:  # 「2」を選択してEnter
```

## Node登録 - Node登録スクリプト実行

### Node1　登録スクリプト実行
```bash
./registerNewNode.sh
仮想Node名を設定してください。各属性間の間は空白を設けないでください。設定例: CN=Alice,OU=Test_Dept,O=R3,L=London,C=GB >>  >> # 「CN=Alice,OU=Test_Dept,O=R3,L=London,C=GB」を入力してEnter
仮想Nodeに含めるCPIのIDを設定してください >> 「CPI 登録スクリプト実行」で作成したCPIのChecksumを設定
```

### Node2 登録スクリプト実行
ヒント: 
1. x500Name: CN=Bob,OU=Test_Dept,O=R3,L=London,C=GB
2. 仮想NodeのCPI: Aliceと同じネットワークに所属したいので...?

---

## Notary登録 - Notary登録スクリプト実行

### Notary CPI登録スクリプト実行
ヒント: 
1. Alice, Bobと同じネットワークに所属したいので、設定すべきMGMのHolding IDは...?
2. Notary CPIに含めるCPB: 「CPI登録スクリプト実行」のときにそれっぽいものを見かけた...?

### Notary Node登録スクリプト実行
```bash
./registerNewNotary.sh
1. x500Name: CN=NotaryRep1,OU=Test_Dept,O=R3,L=London,C=GB
2. 仮想NodeのCPI: ノーヒントでがんばってください！
```

---

## Flow実行
### Alice Node -> Bob Node: CreateNewChatFlow実行(Alice -> Bob Chat開始(1/3))
SwaggerUI で Alice の shortHash を設定

### 以下を「POST /flow/{holdingidentityshorthash}」のbodyに設定(Alice -> Bob Chat開始(2/3))
{
    "clientRequestId": "create-1",
    "flowClassName": "com.r3.developers.cordapptemplate.utxoexample.workflows.CreateNewChatFlow",
    "requestBody": {
        "chatName":"Chat with Bob",
        "otherMember":"CN=Bob, OU=Test_Dept, O=R3, L=London, C=GB",
        "message": "Hello Bob"
        }
}

### Server response に 202 が返ってきていることを確認。(Alice -> Bob Chat開始(3/3))

### 結果の取り出しは、GET /flow/{holdingidentityshorthash} /{clientrequestid}で確認(Alice -> Bob Chat送信実行結果確認)

### Bob Node: ListChatsFlow実行(Bob ChatList実行(1/3))
SwaggerUI で Bob の shortHash を設定

### 以下を「POST /flow/{holdingidentityshorthash}」のbodyに設定(Bob ChatList実行(2/3))
{
    "clientRequestId": "list-1",
    "flowClassName": "com.r3.developers.cordapptemplate.utxoexample.workflows.ListChatsFlow",
    "requestBody": {}
}

### Server response に 202 が返ってきていることを確認。(Bob ChatList実行(3/3))

### 結果の取り出しは、GET /flow/{holdingidentityshorthash} /{clientrequestid}で確認(Bob ChatList実行結果確認)

### Bob Node -> Alice Node UpdateChatFlow(Bob -> Alice Chat更新(1/3))
SwaggerUI で Bob の shortHash を設定

### 以下を「POST /flow/{holdingidentityshorthash}」のbodyに設定(Bob -> Alice Chat更新(2/3))
{
 "clientRequestId": "update-1",
 "flowClassName": "com.r3.developers.cordapptemplate.utxoexample.workflows.UpdateChatFlow",
 "requestBody": {
     "id":"53825b45-aa57-4af7-9db3-be9f2fd1732c",
     "message": "Nice to meet you, Alice"
     }
}

### Server response に 202 が返ってきていることを確認。(Bob -> Alice Chat更新(3/3))

### 結果の取り出しは、GET /flow/{holdingidentityshorthash} /{clientrequestid}で確認(Bob Chat更新実行結果確認)

### [問題]Alice Node: ListChatsFlow実行
