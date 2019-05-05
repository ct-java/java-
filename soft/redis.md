# redis安装
## 安装环境
### 安装gcc
`yum install gcc-c++`
`yum -y install gcc automake autoconf libtool make`
## 安装步骤
1. redis的源码包上传到linux系统
2. 解压缩redis
3. 编译。进入redis源码目录。make
4. 安装。make install PREFIX=/usr/local/redis

PREFIX参数指定redis的安装目录。一般软件安装到/usr/local/redis目录下

## 连接redis
### 启动服务端
#### 前端启动：
在redis的安装目录下直接启动 `./redis-server `
#### 后台启动：
把/root/redis-3.0.0/redis.conf复制到/usr/local/redis/bin目录下`cp redis.conf /usr/local/redis/bin/`
修改配置文件
```properties
daemonize yes
```
`./redis-server redis.conf`
### 查看进程
`ps aux|grep redis`
### 启动客户端
在安装目录下：`./redis-cli`
默认连接localhost运行在6379端口的redis服务
#### 指定启动参数启动
`./redis-cli -h 192.168.18.130 -p 6379`
其中：
-h：连接的服务器的地址
-p：服务的端口号
### 关闭redis
在安装目录下：`./redis-cli shutdown`
# jedis
jedis是redis的java客户端，spring将redis连接池作为一个bean配置。
## jedis单机配置
1. `redis.clients.jedis.ShardedJedisPool`，这是基于hash算法的一种分布式集群redis客户端连接池。
2. `redis.clients.jedis.JedisPool`，这是单机环境适用的redis连接池
## jedis集群配置
