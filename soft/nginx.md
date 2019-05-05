# 安装上传插件lrzsz
## 安装
`yum -y install lrzsz`
## 上传
rz
## 下载
sz
***
# nginx按照环境
## gcc
安装nginx需要先将官网下载的源码进行编译，编译依赖gcc环境，如果没有gcc环境，需要安装gcc：`yum install gcc-c++`
## PCRE
PCRE(Perl Compatible Regular Expressions)是一个Perl库，包括 perl 兼容的正则表达式库。nginx的http模块使用pcre来解析正则表达式，所以需要在linux上安装pcre库。
`yum install -y pcre pcre-devel`
注：pcre-devel是使用pcre开发的一个二次开发库。nginx也需要此库。
## zlib
zlib库提供了很多种压缩和解压缩的方式，nginx使用zlib对http包的内容进行gzip，所以需要在linux上安装zlib库。`yum install -y zlib zlib-devel`
## openssl
OpenSSL 是一个强大的安全套接字层密码库，囊括主要的密码算法、常用的密钥和证书封装管理功能及SSL协议，并提供丰富的应用程序供测试或其它目的使用。nginx不仅支持http协议，还支持https（即在ssl协议上传输http），所以需要在linux安装openssl库。`yum install -y openssl openssl-devel`
# 编译安装
1. 将nginx-1.8.0.tar.gz拷贝至linux服务器
2. 解压
`tar -zxvf nginx-1.8.0.tar.gz`
`cd nginx-1.8.0`
3. 查看详细参数
./configure --help
```properties
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi
```
==注意：上边将临时文件目录指定为/var/temp/nginx，需要在/var下创建temp及nginx目录==
如果没有make命令`yum -y install gcc automake autoconf libtool make`
## 编译安装
- 编译
`make`
- 安装
`make install`
- 查看
`cd ../nginx`
# nginx操作

## 启动
`/usr/local/nginx/sbin/nginx`
## 查看进程
`ps aux | grep nginx`

15098是nginx主进程的进程id，15099是nginx工作进程的进程id
> 注意：执行 ./nginx 启动nginx，这里可以-c指定加载的nginx配置文件，如下：
./nginx -c /usr/local/nginx/conf/nginx.conf
如果不指定-c，nginx在启动时默认加载conf/nginx.conf文件，此文件的地址也可以在编译安装nginx时指定./configure的参数（--conf-path= 指向配置文件（nginx.conf））
## 停止
- 方式一：快速停止
`/usr/local/nginx/sbin/nginx -s stop`
此方式相当于先查出nginx进程id再使用kill命令强制杀掉进程
- 方式二：优雅停止（建议使用）
`/usr/local/nginx/sbin/nginx -s quit`
此方式停止步骤是待nginx进程处理任务完毕进行停止
## 重启
- 方式一：重新加载配置文件
`/user/local/nginx/sbin/nginx -s reload`
- 方式二：先停止再启动（建议使用）
`/usr/local/nginx/sbin/nginx -s quit`
`/usr/local/nginx/sbin/nginx`
# 测试
nginx安装成功，启动nginx，即可访问虚拟机上的nginx
![图片1](/assets/图片1.png)
# 设置开机自启动
## 编写shell脚本
这里使用的是编写shell脚本的方式来处理
vi /etc/init.d/nginx  (输入下面的代码)

```shell
#!/bin/bash
# nginx Startup script for the Nginx HTTP Server
# it is v.0.0.2 version.
# chkconfig: - 85 15
# description: Nginx is a high-performance web and proxy server.
#              It has a lot of features, but it's not for everyone.
# processname: nginx
# pidfile: /var/run/nginx.pid
# config: /usr/local/nginx/conf/nginx.conf
nginxd=/usr/local/nginx/sbin/nginx
nginx_config=/usr/local/nginx/conf/nginx.conf
nginx_pid=/var/run/nginx.pid
RETVAL=0
prog="nginx"
# Source function library.
. /etc/rc.d/init.d/functions
# Source networking configuration.
. /etc/sysconfig/network
# Check that networking is up.
[ ${NETWORKING} = "no" ] && exit 0
[ -x $nginxd ] || exit 0
# Start nginx daemons functions.
start() {
if [ -e $nginx_pid ];then
   echo "nginx already running...."
   exit 1
fi
   echo -n $"Starting $prog: "
   daemon $nginxd -c ${nginx_config}
   RETVAL=$?
   echo
   [ $RETVAL = 0 ] && touch /var/lock/subsys/nginx
   return $RETVAL
}
# Stop nginx daemons functions.
stop() {
        echo -n $"Stopping $prog: "
        killproc $nginxd
        RETVAL=$?
        echo
        [ $RETVAL = 0 ] && rm -f /var/lock/subsys/nginx /var/run/nginx.pid
}
# reload nginx service functions.
reload() {
    echo -n $"Reloading $prog: "
    #kill -HUP `cat ${nginx_pid}`
    killproc $nginxd -HUP
    RETVAL=$?
    echo
}
# See how we were called.
case "$1" in
start)
        start
        ;;
stop)
        stop
        ;;
reload)
        reload
        ;;
restart)
        stop
        start
        ;;
status)
        status $prog
        RETVAL=$?
        ;;
*)
        echo $"Usage: $prog {start|stop|restart|reload|status|help}"
        exit 1
esac
exit $RETVAL
```
`:wq`  保存并退出`

## 设置文件的访问权限
`chmod a+x /etc/init.d/nginx`  (a+x ==> all user can execute  所有用户可执行)
`/etc/init.d/nginx status`
> 如果修改了nginx的配置文件nginx.conf，也可以使用上面的命令重新加载新的配置文件并运行，可以将此命令加入到rc.local文件中，这样开机的时候nginx就默认启动了
## 加入到rc.local文件中
`vi /etc/rc.local`
加入一行 `/etc/init.d/nginx start` 保存并退出，下次重启会生效
