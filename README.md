# corda5-dev-training-sbir3japan

こちらはSBI R3 Japanが提供するCordaトレーニングの開発ハンズオン用のテストコードです。

# 使用環境

本トレーニング環境は、以下の環境での動作を確認しています。

| Software | Version                                                                             |
|------------|-------------------------------------------------------------------------------------|
| Operating systems | - Mac OS (intel and ARM)<br>- Windows 10/11<br>- Linux                              
| Java | Azul Zulu JDK 17 (Other versions should work but have not been extensively tested.) |
| Intellij | v2021.1.3 ~ 2022.3.3 community edition                                                        |
| git | ~v2.24.1                                                                            |
| docker | Docker Desktop v4.25.0                                    |
| Corda CLI | 後述                                                                                  |

# Corda CLI

Corda CLI（command line interface）は、Corda Package Installer（CPI）作成やCordaクラスタ管理など、Cordaに関する様々なタスクをサポートするコマンドラインツールです。
CorDappsを開発するためのGradle Pluginは、バックグラウンドでCorda CLIを使用するため、開発環境へのインストールが必要です。

1. インストーラーのダウンロード
   CordaのGit Hub([こちら](https://github.com/corda/corda-runtime-os/releases))クリックし、ローカルにインストーラー「corda-cli-installer-5.2.1.0.zip」をダウンロードします。
2. 配置フォルダ初期化(オプション)
   ローカルPCにフォルダ<user-home>/.corda/cliがすでに存在している場合は削除します。
3. cliインストール
   cliをインストールします。実行前にzipファイルを配置したフォルダに移動してください。WindowsとLinux/macOSでコマンドが異なりますのでご注意ください。

## 【Windows】
### zipファイル展開
``` ps1
Expand-Archive .\corda-cli-installer-5.2.1.0.zip
```
### corda-cli-installer-5.0.0.0移動
``` ps1
cd corda-cli-installer-5.2.1.0
```
### インストールシェル実行
``` ps1
.\install.ps1
```
上記シェルが完了したら、<user-home>/.corda配下にcliフォルダが作成されます。 端末の環境変数PATHに「<user-home>/.corda/cli」を通します。  
任意のフォルダにて以下のコマンドをPowerShellより実行して、ヘルプが標準出力に表示されればインストール成功です。
``` ps1
corda-cli.cmd -h
```
## 【Mac/Linux】
### zipファイル展開
``` bash
unzip ./corda-cli-installer-5.0.0.0.zip -d corda-cli-installer-5.0.0.0
```
### corda-cli-installer-5.0.0.0移動
```
cd corda-cli-installer-5.0.0.0
```
### インストールシェル実行
```
./install.sh
```
上記シェルが完了したら、<user-home>/.corda配下にcliフォルダが作成されます。 端末の環境変数PATHに「<user-home>/.corda/cli」を通します。  
任意のフォルダにて以下のコマンドをPowerShellより実行して、ヘルプが標準出力に表示されればインストール成功です。
```
corda-cli.sh -h
```