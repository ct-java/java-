# OpenSSL实验：
## 自签名证书
### 自签名证书的应用场景
1. 用于测试
2. 用于构建CA
3. 颁发者和颁发的目的对象是同一个人
### [服务器端，生成根证书]
#### 生成私有密钥，需要输入密码
- openssl req `-x509` -newkey rsa:2048 -keyout cal.key -out cal.cer
![OpenSSL实验](/assets/OpenSSL实验.png)
#### 查看根证书信息
只查看颁发者和颁发给谁？
- openssl x509 -in cal.cer `-subject` `-issuer` -noout
查看根证书所有信息
- openssl x509 -in cal.cer -text | more
![OpenSSL-subject](/assets/OpenSSL-subject.png)
### [客户端] 签署颁发证书
#### 申请主体：生成密钥对（私有密钥和请求文件）
- openssl req -nodes -newkey rsa:2048 -keyout client.key -out `client.req` -subj /CN=client
#### CA服务器：签署证书
- openssl x509 -req -in `client.req` -out client.cer -CA cal.cer -CAkey [cal.key] -CAcreateserial [xxx.ser] -days 3650
> 第一次CAcreateserial
![openssl-sign](/assets/openssl-sign_iu3x8gkeg.png)
