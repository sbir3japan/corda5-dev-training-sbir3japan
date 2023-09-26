# corda5-dev-training-sbir3japan

こちらはSBI R3 Japanが提供するCordaトレーニングの開発ハンズオン用のテストコードです。

# 使用環境

使用環境はR3が提供しているCorda docに準拠します。

| Software | Version |
|------------|------------|
| Operating systems | - Mac OS (intel and ARM)<br>- Windows 10/11<br>- Linux
| Java | Azul Zulu JDK 11 (Other versions should work but have not been extensively tested.) |
| Intellij | ~v2021.X.Y community edition |
| git | ~v2.24.1 |
| docker | Docker Engine ~v20.X.Y or Docker Desktop ~v3.5.X |
| Corda CLI | 後述 |

# Corda CLI

Corda CLI（command line interface）は、Corda Package Installer（CPI）作成やCordaクラスタ管理など、Cordaに関する様々なタスクをサポートするコマンドラインツールです。
CorDappsを開発するためのCorDapp Standard Development Environment (CSDE)は、バックグラウンドでCorda CLIを使用するため、開発環境へのインストールが必要です。

1. インストーラーのダウンロード
   以下のリンクをクリックし、ローカルにインストーラー「platform-jars-5.0.0.tar.gz」をダウンロードします。
(Start building for freeをご記入すると、ダウンロード可能になります。「platform-jars-5.0.0.tar」という名前難しい場合はご連絡ください)

https://developer.r3.com/next-gen-corda/#get-corda

2. 配置フォルダ初期化(オプション)
   ローカルPCにフォルダ<user-home>/.corda/cliがすでに存在している場合削除します。
3. cliインストール
   cliをインストールします。実行前にzipファイルを配置したフォルダに移動してください。WindowsとLinux/macOSでコマンドが異なりますのでご注意ください。

   【Windows】
   ``` ps1
   # zipファイル展開
   Expand-Archive .\corda-cli-installer-5.0.0.0.zip
   # corda-cli-installer-5.0.0.0移動
   cd corda-cli-installer-5.0.0.0
   # インストールシェル実行
   .\install.ps1
   # 上記シェルが完了したら、<user-home>/.corda配下にcliフォルダが作成されます。 端末の環境変数PATHに「<user-home>/.corda/cli」を通します。
   任意のフォルダにて以下のコマンドをPowerShellより実行して、ヘルプが標準出力に表示されればインストール成功です。
   corda-cli.cmd -h
   ```
   【Mac/Linux】
   ``` bash
   # zipファイル展開
   unzip ./corda-cli-installer-5.0.0.0.zip -d corda-cli-installer-5.0.0.0
   # corda-cli-installer-5.0.0.0移動
   cd corda-cli-installer-5.0.0.0
   # インストールシェル実行
   ./install.sh
   # 上記シェルが完了したら、<user-home>/.corda配下にcliフォルダが作成されます。 端末の環境変数PATHに「<user-home>/.corda/cli」を通します。
   任意のフォルダにて以下のコマンドをPowerShellより実行して、ヘルプが標準出力に表示されればインストール成功です。
   corda-cli.sh -h
   ```
