# 安装教程

## 基本教程

### 首先编辑配置文件

-   此文件最后几行可以做适当的修改

```properties
set KEY_COUNTRY=CN
set KEY_PROVINCE=SiChuan
set KEY_CITY=ChengDu
set KEY_ORG=OpenVPN
set KEY_EMAIL=19982838543@163.com
set KEY_CN=lsdm
set KEY_NAME=lsdm
set KEY_OU=lsdm
set PKCS11_MODULE_PATH=lsdm
set PKCS11_PIN=1234
```

### 生成密钥和证书

> 在当前目录下打开cmd命令面板
> 依次运行：

-   init-config.bat
-   vars.bat
-   clean-all.bat
    接着创建证书：
-   build-ca.bat
    中途一直回车就行
    接着执行：
-   build-dh.bat
-   build-key-server.bat server
    中间可以设置密码【A challeng password\[]: 】
-   build-key.bat client
    中间可设置一样的密码【A challeng password\[]: 】
-   openvpn --genkey --secret ta.key

### 随后会生成相应的密钥和证书

![openvpn-keys](/assets/openvpn-keys.png)

### 服务器密钥/证书文件

![openvpn-service](/assets/openvpn-service.png)

### 客户端密钥/证书文件

![openvpn-client](/assets/openvpn-client.png)

<https://wiki.mikrotik.com/wiki/OpenVPN>
<https://www.techspot.com/>

# 启动配置文件

## server.ovpn解析

```properties
##############################################
# 示例的OpenVPN 2.0配置文件
# 多客户端服务器。
#
# 此文件用于服务器端
# 多个客户端 -> 一个服务器
# OpenVPN配置
#
# OpenVPN也支持single-machine单机
# configuration（参见示例页面在网站上获取更多信息）
#
# 此配置应该适用于Windows或Linux/BSD系统。
# 记住Windows引用路径名并使用双反斜杠，
# 例如：“C\\Program Files\\OpenVPN\\config\\foo.key”
#
# 评论以'#'或';'开头
##############################################
#
# WordVPN应该使用哪个本地IP地址listen on? (optional)
;local a.b.c.d
#
# OpenVPN应该监听哪个TCP/UDP端口？
# 如果要运行多个OpenVPN实例：
# 需要在同一台机器上，使用不同的端口
# 你需要在防火墙上打开此端口。
port 1194
#
# TCP或UDP服务器？
;proto tcp
proto udp
#
# “dev tun”将创建路由IP隧道，
#
# 注：tun俗称路由模式，tap俗称网桥模式
# 注：tap在二层，tun在三层
# 注：tap可以直接使用route这样的路由表命令，但不能用于手机设备
# 注：tun可以用于手机，但不能使用route这样的路由表命令，压根无法穿透
# 注意：tap往往结合路由表进行设定下一跳，tun则往往要和iptables集合紧密来实现下一跳
# 结论：如果想要组件VPN实现路由命令进行路由网关的多级跳转，那么需要搭建tap网桥模式
# 如果使用了tun模式，想要实现nat转发，那么你只能选择使用iptables来实现
#
# “dev tap”将创建一个以太网隧道。
#
# 如果您是以太网桥接：
# 请使用“dev tap0”并预先创建了一个tap0虚拟接口，并使用以太网接口桥接它
#
# 如果要控制访问策略：
# 通过VPN，您必须创建防火墙
#
# TUN/TAP接口的规则：
# 在非Windows系统上，您可以给予显式单元号，例如tun0
#
# 在Windows上，为此使用“dev-node”
# 在大多数系统上，VPN不起作用
# 除非您部分或完全禁用
# TUN/TAP接口的防火墙
;dev tap
dev tun

# Windows需要TAP-Win32适配器名称
# 来自“网络连接”面板（如果您）有多个。
#
# 在XP SP2或更高版本上：
# 您可能需要有选择地禁用TAP适配器的Windows防火墙。
#
# 非Windows系统通常不需要此功能。
;dev-node MyTap
#
# SSL/TLS根证书（ca），
# 证书（cert）和私钥（密钥）。
# 每个客户和服务器必须有自己的证书key文件。
# 服务器和所有客户端都会使用相同的ca文件。
#
# 查看系列的“easy-rsa”目录
# 用于生成RSA证书的脚本数和私钥。
# 记得要用服务器的唯一通用名称和每个客户端证书。
#
# 可以使用任何X509密钥管理系统。
# OpenVPN也可以使用PKCS12格式的密钥文件
# （参见手册页中的“pkcs12”指令）。
ca ca.crt
cert server.crt
key server.key  #这个文件应该保密

# 指定迪菲·赫尔曼参数。
# 通过以下命令可以生成自己的dbxxxx.pem：
# openssl dhparam -out dh2048.pem 2048
# 如果你使用的是2048位密钥，使用2048替换其中的1024
dh dh2048.pem

# 网络拓扑结构：
# 需要是（通过IP寻址）的子网
# 除非Windows客户端版本号是v2.0.9及更低
# 否则必须支持(then net30, i.e. a /30 per client)
# 默认为 net30（不推荐）
;topology subnet

# 配置服务器模式并提供VPN子网
# 从OpenVPN中抽取客户端地址。
# 服务器本身需要10.8.0.1，
# 其余的将提供给客户。
# 每个客户端都可以访问服务器
# 10.8.0.1。如果你是的话，请注释这一行
# ethernet桥接。有关详细信息，请参见手册页。
server 10.8.0.0 255.255.255.0

# 维护客户端 <-> 虚拟IP地址的记录
# 该文件中的关联。
# 如果OpenVPN关闭或重新启动，可以分配重新连接的客户端
# 池中的相同虚拟IP地址
# 先前已分配。
ifconfig-pool-persist ipp.txt

# 配置以太网桥接的服务器模式[TAP]。
# 您必须首先使用操作系统的桥接功能
# 将TAP接口与以太网网卡接口进行桥接。
# 你需要手动设置桥接接口的IP地址、子网掩码，
# 这里我们假设为10.8.0.4/255.255.255.0。
# 最后我们必须在此子网中留出IP范围
# 分配（start=10.8.0.50 end=10.8.0.100）
# 如果你不是以太网桥接模式，直接注释掉这行指令即可
;server-bridge 10.8.0.4 255.255.255.0 10.8.0.50 10.8.0.100

# 配置以太网桥接的服务器模式
# 该指令仅针对使用DHCP代理的以太网桥接模式。
# 此时客户端将请求服务器端的DHCP服务器，
# 从而获得分配给它的IP地址和DNS服务器地址。
# 在此之前，你也需要先将以太网网卡接口和TAP接口进行桥接
# 注意：此模式仅适用于客户端（例如Windows），客户端TAP适配器所在的位置
# 绑定到DHCP客户端。
;server-bridge

# 将路由推送到客户端以允许客户端连接到后面的其他私有子网
# (简而言之，就是允许客户端访问VPN服务器自身所在的其他局域网)
# 记住，这些私有子网也要将OpenVPN客户端的地址池
# (10.8.0.0/255.255.255.0)反馈回OpenVPN服务器。
;push "route 192.168.10.0 255.255.255.0"
;push "route 192.168.20.0 255.255.255.0"


# 为指定的客户端分配指定的IP地址，或者客户端背后也有一个私有子网想要访问VPN，
# 那么你可以针对该客户端的配置文件使用ccd子目录。
# (简而言之，就是允许客户端所在的局域网成员也能够访问VPN)

# 举个例子：
# 假设有个Common Name为"Thelonious"的客户端背后也有一个小型子网想要连接到VPN，
# 该子网为192.168.40.128/255.255.255.248。
# 首先，你需要去掉下面两行指令的注释：
;client-config-dir ccd
;route 192.168.40.128 255.255.255.248
#
# 然后创建一个文件ccd/Thelonious：文件的内容为：
# iroute 192.168.40.128 255.255.255.248
# 这将允许Thelonious的私有子网访问VPN。
# 注意，这个指令只能在你是基于路由、而不是基于桥接的模式下才能生效。
# 比如，你使用了"dev tun"和"server"指令。

# 再举个例子：
# 假设你想给给Thelonious分配一个固定的VPN IP地址为 10.9.0.1。
# 首先你需要先取消下面两行的注释：
;client-config-dir ccd
;route 10.9.0.0 255.255.255.252
# 然后将文件ccd/helonious中添加如下指令：
# ifconfig-push 10.9.0.1 10.9.0.2


# 如果你想要为不同群组的客户端启用不同的防火墙访问策略，你可以使用如下两种方法：
# (1)运行多个OpenVPN守护进程，
# 每个进程对应一个群组，并为每个进程(群组)启用适当的防火墙规则。
# (2) (进阶)创建一个脚本来动态地修改响应于来自不同客户的防火墙规则。
# 关于learn-address脚本的更多信息请参考官方手册页面。
;learn-address ./script


# 如果启用该指令，所有客户端的默认网关都将重定向到VPN，
# 这将导致诸如web浏览器、DNS查询等所有客户端流量都经过VPN。
# (为确保能正常工作，
# OpenVPN服务器所在计算机
# 可能需要在TUN/TAP接口
# 与以太网之间使用NAT或桥接技术进行连接)
;push "redirect-gateway def1 bypass-dhcp"

# 某些具体的Windows网络设置可以被推送到客户端，例如DNS或WINS服务器地址。
# 下列地址来自openns.com提供的DNS服务器。
;push "dhcp-option DNS 208.67.222.222"
;push "dhcp-option DNS 208.67.220.220"

# 取消注释此指令以允许不同的客户能够“看到”彼此。
# 默认情况下，客户端只能看到服务器。
# 为了确保客户端只能看到服务器
# 还需要适当的防火墙，你还可以在服务器端的TUN/TAP接口上设置适当的防火墙规则
# server的TUN / TAP接口。
;client-to-client


# 如果多个客户端可能使用相同的证书/私钥文件或Common Name进行连接，
# 那么你可以取消该指令的注释。
# 建议该指令仅用于测试目的。
# 对于生产使用环境而言，每个客户端都应该拥有自己的证书和私钥。
# 如果你没有为每个客户端分别生成Common Name唯一的证书/私钥，
# 你可以取消该行的注释(但不推荐这样做)。
;duplicate-cn

# keepalive指令将导致类似于ping命令的消息被来回发送，
# 以便于服务器端和客户端知道对方何时被关闭。
# 每10秒钟ping一次，如果120秒内都没有收到对方的回复，则表示远程连接已经关闭
keepalive 10 120

# 除了提供的额外安全性
# 通过SSL/ LS创建“HMAC防火墙”
# 帮助阻止DoS攻击和UDP端口泛滥。
#
# 生成ta.key文件：
# openvpn --genkey --secret ta.key
#
# 服务器和每个客户端必须具有
# 这个密钥的副本。
# 在服务器上，这个参数设置为'1'
# 在客户端上，这个参数设置为'0'
tls-auth ta.key 0 # 这个文件是秘密的

# 选择加密算法
# 该配置项也必须复制到每个客户端配置文件中。
cipher AES-256-CBC

# 在VPN连接上启用压缩传输。
# 如果你在此处启用了该指令，那么也应该在每个客户端配置文件中启用它
;compress lz4-v2
;push "compress lz4-v2"
#
# 对于与旧客户兼容的压缩，请使用comp-lzo
# 如果你在这里启用它，你也必须
# 在客户端配置文件中启用它。
;comp-lzo

# 允许并发连接的客户端的最大数量
;max-clients 100

# 在完成初始化工作之后，降低OpenVPN守护进程的权限是个不错的主意。
# 该指令仅限于非Windows系统中使用。
;user nobody
;group nobody

# 持久化选项可以尽量避免访问那些在
# 重启之后由于用户权限降低
# 而无法访问的某些资源
persist-key
persist-tun

# 输出一个简短的状态文件，用于显示当前的连接状态，
# 该文件每分钟都会清空并重写一次。
status openvpn-status.log

# 默认情况下，日志消息将写入syslog
# (在Windows系统中，如果以服务方式运行，日志消息将写入OpenVPN安装目录的log文件夹中)
# 你可以使用log或者log-append来改变这种默认情况。
# "log"方式在每次启动时都会清空之前的日志文件。
# "log-append"这是在之前的日志内容后进行追加。
# 你可以使用两种方式之一(但不要同时使用)。
;log         openvpn.log
;log-append  openvpn.log

# 为日志文件设置适当的冗余级别(0~9)。冗余级别越高，输出的信息越详细。
#
# 0 表示静默运行，只记录致命错误。
# 4 表示合理的常规用法。
# 5 和 6 可以帮助调试连接错误。
# 9 表示极度冗余，输出非常详细的日志信息。
verb 3

# 重复信息的沉默度。
# 相同类别的信息只有前20条会输出到日志文件中。
;mute 20

# 通知客户端服务器重新启动时，
# 能够自动重新连接。
explicit-exit-notify 1
```

## client.ovpn详解

```properties
##############################################
# 示例客户端OpenVPN 2.0配置文件
# 用于连接多客户端服务器。
#
# 这个配置可以被多个使用
# 客户端，但每个客户端应该
# 自己的证书和密钥文件。
#
# 在Windows上，您可能想要重命名此
# file所以它有一个.ovpn扩展名
##############################################

# 指定当前VPN是客户
# 将拉动某些来自服务器配置文件指令进行初始化
client

 #必须与服务器端的保持一致
;dev tap
dev tun

# 与服务器保持一致
;dev-node MyTap

# 与服务器保持一致
;proto tcp
proto udp

# 服务器的主机名/ IP和端口。
# 您可以拥有多个远程条目，
# 在服务器之间进行负载平衡。
remote my-server-1 1194
;remote my-server-2 1194

# 从远程主机列表中随机选择一个主机
# 否则按指定的顺序尝试主机。
;remote-random

# 不断的无限地尝试解析OpenVPN服务器的主机名。
# 对于没有永久连接的机器比如笔记本电脑来说是非常有用的
# resolv-retry infinite

# 大多数客户端不需要绑定
# 一个特定的本地端口号。
nobind

# 初始化后降级权限（仅限非Windows）
;user nobody
;group nobody

# 重启时保证和先前的状态一致
persist-key
persist-tun

# 如果你通过连接HTTP代理到达实际的OpenVPN
# server，把代理服务器/IP和端口号在这里。
# 请参见手册页
# 如果您的代理服务器需要认证
;http-proxy-retry # retry on connection failures
;http-proxy [proxy server] [proxy port #]

# 无线网络经常产生很多重复数据包。
# 设置此标志表示沉默重复数据包警告。
;mute-replay-warnings

# SSL/TLS 参数
# 查看服务器配置文件以获取更多信息
# 最好用一个单独的.crt/.key文件
# 对为每个客户，一个单一的.ca文件可用于所有客户端。
ca ca.crt
cert client.crt
key client.key

# 通过检查certicate是否具有正确的密钥使用设置来验证服务器证书。
# 这是一个重要的预防措施，以防止潜在的攻击
# 更多细节访问：
# http://openvpn.net/howto.html#mitm
#
# 要使用此功能，您需要生成将您的服务器证书
# 并把keyUsage设置为digitalSignature，
# 密钥加密和扩展到serverAuth EasyRSA的密钥使用可以为您做到这一点
remote-cert-tls server

# 如果服务器上使用了tls-auth密钥
# 然后每个客户端也必须有密钥。
# 注意：客户端值必须为：“1”
tls-auth ta.key 1

# 和服务器保持一致
cipher AES-256-CBC

# 和服务器保持一致
# COMP-LZO

# 设置日志文件详细程度。
verb 3

# Silence重复消息
;mute 20
```
