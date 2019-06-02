在当前目录下打开cmd命令面板
依次运行：
init-config.bat
vars.bat
clean-all.bat
接着创建证书：
Build-ca.bat
中途一直回车就行
接着执行：
Build-dh.bat
Build-key-server.bat server
中间可以设置密码【A challeng password\[]: 】
Build-key.bat client
中间可设置一样的密码【A challeng password\[]: 】
 openvpn --genkey --secret ta.key

<https://wiki.mikrotik.com/wiki/OpenVPN>
<https://www.techspot.com/>

# server.ovpn解析

```ini
##############################################
＃ 示例的OpenVPN 2.0配置文件
＃ 多客户端服务器。
＃
＃ 此文件用于服务器端
＃ 多个客户端＃  - >一个服务器
＃ OpenVPN配置。
＃
＃ OpenVPN也支持＃
＃ single-machine < - >单机
＃ configuration（参见示例页面
＃ 在网站上获取更多信息）。
＃
＃ 此配置应该适用于Windows
＃ 或Linux / BSD系统。记住
＃ Windows引用路径名并使用
＃ double反斜杠，例如：
＃ “C\\Program Files\\OpenVPN\\config\\foo.key”
＃
＃ 评论以'＃ '或';'开头
##############################################

＃ WordVPN应该使用哪个本地IP地址
＃  listen on? (optional)
;local a.b.c.d

＃ OpenVPN应该监听哪个TCP / UDP端口？
＃ 如果要运行多个OpenVPN实例
＃ 在同一台机器上，使用不同的端口
＃ 每个＃ 号。你需要
＃ 在防火墙上打开此端口。
port 1194

＃ TCP或UDP服务器？
; proto tcp
proto udp

＃ “dev tun”将创建路由IP隧道，
＃ “dev tap”将创建一个以太网隧道。
＃ 如果您是以太网桥接，请使用“dev tap0”
＃ 并预先创建了一个tap0虚拟接口
＃ 并使用以太网接口桥接它。
＃ 如果要控制访问策略
＃ 通过VPN，您必须创建防火墙
＃ TUN / TAP接口的规则。
＃ 在非Windows系统上，您可以给予
＃ 显式单元号，例如tun0。
＃ 在Windows上，为此使用“dev-node”。
＃ 在大多数系统上，VPN不起作用
＃ 除非您部分或完全禁用
＃ TUN / TAP接口的防火墙。
;dev tap
dev tun

＃ Windows需要TAP-Win32适配器名称
＃ 来自“网络连接”面板（如果您）
＃ 有多个。在XP SP2或更高版本上，
＃ 您可能需要有选择地禁用
＃ TAP适配器的Windows防火墙。
＃ 非Windows系统通常不需要此功能。
;dev-node MyTap

＃ SSL / TLS根证书（ca），证书
＃ （cert）和私钥（密钥）。每个客户
＃ 和服务器必须有自己的证书和
＃ key文件。服务器和所有客户端都会
＃ 使用相同的ca文件。
＃
＃ 查看系列的“easy-rsa”目录
＃ 用于生成RSA证书的脚本数
＃ 和私钥。记得要用
＃ 服务器的唯一通用名称
＃ 和每个客户端证书。
＃
＃ 可以使用任何X509密钥管理系统。
＃ OpenVPN也可以使用PKCS＃ 12格式的密钥文件
＃ （参见手册页中的“pkcs12”指令）。
ca ca.crt
cert server.crt
key server.key ＃ 这个文件应该保密

＃ Diffie hellman参数。
＃ 生成自己的：
＃ openssl dhparam -out dh2048.pem 2048
dh dh2048.pem

＃  网络拓扑结构
＃ 应该是子网（通过IP寻址）
＃ 除非Windows客户端v2.0.9及更低版本必须
＃ bepport（然后是net30，即每个客户端30个）
＃ 默认为net30（不推荐）
;topology subnet

＃ 配置服务器模式并提供VPN子网
＃ 从OpenVPN中抽取客户端地址。
＃ 服务器本身需要10.8.0.1，
＃ 其余的将提供给客户。
＃ 每个客户端都可以访问服务器
＃ 10.8.0.1。如果你是的话，请注释这一行
＃ ethernet桥接。有关详细信息，请参见手册页。
server 10.8.0.0 255.255.255.0

＃ 维护客户端< - >虚拟IP地址的记录
＃ 该文件中的关联。如果OpenVPN关闭或
＃ 重新启动，可以分配重新连接的客户端
＃ 池中的相同虚拟IP地址
＃ 先前已分配。
ifconfig-pool-persist ipp.txt

＃ 配置以太网桥接的服务器模式。
＃ 您必须首先使用操作系统的桥接功能
＃ 将TAP接口与以太网桥接
＃ NIC接口。然后你必须手动设置
＃ 在桥接口上的＃ IP / netmask，这里我们
＃ assume 10.8.0.4/255.255.255.0。最后我们
＃ 必须在此子网中留出IP范围
＃ 分配＃ （start = 10.8.0.50 end = 10.8.0.100）
＃ 连接客户端。留下这一行评论
＃ out除非你是以太网桥接。
;server-bridge 10.8.0.4 255.255.255.0 10.8.0.50 10.8.0.100

＃ 配置以太网桥接的服务器模式
＃ 使用DHCP代理，客户端通话
＃ 到OpenVPN服务器端DHCP服务器
＃ 接收其IP地址分配
＃ 和DNS服务器地址。你必须先使用
＃ 您的操作系统桥接TAP的桥接功能
＃ internet与以太网NIC接口。
＃ 注意：此模式仅适用于客户端（例如
＃ Windows），客户端TAP适配器所在的位置
＃ 绑定到DHCP客户端。
;server-bridge

＃ 将路由推送到客户端以允许它
＃ 到达后面的其他私有子网
＃ 服务器。记住这些
＃ 私有子网也需要
＃ 知道路由OpenVPN客户端
＃ address pool（10.8.0.0/255.255.255.0）
＃ 回到OpenVPN服务器。
;push "route 192.168.10.0 255.255.255.0"
;push "route 192.168.20.0 255.255.255.0"

＃ 将特定IP地址分配给特定IP地址
＃ 客户端或连接客户端有私有
＃ 后面的子网也应该有VPN访问权限，
＃ 使用子目录“ccd”进行特定于客户端
＃ 配置文件（有关详细信息，请参见手册页）。

＃ 例子：假设客户端
＃ 拥有证书通用名称“Thelonious”
＃ 在连接后面还有一个小子网
＃ machine，例如192.168.40.128/255.255.255.248。
＃ 首先，取消注释这些行：
;client-config-dir ccd
;route 192.168.40.128 255.255.255.248
＃ 然后用这一行创建一个文件ccd / Thelonious：
＃ iroute 192.168.40.128 255.255.255.248
＃ 这将允许Thelonious的私有子网
＃ 访问VPN。这个例子只会起作用
＃ 如果你是路由，而不是桥接，即你是
＃ 使用“dev tun”和“server”指令。

＃ 例子：假设你想给
＃ Thelonious一个固定的VPN IP地址为10.9.0.1。
＃ 首先取消注释这些行：
;client-config-dir ccd
;route 10.9.0.0 255.255.255.252
＃ 然后将此行添加到ccd / Thelonious：
＃ ifconfig-push 10.9.0.1 10.9.0.2

＃ 假设您要启用不同的
＃ 不同组的防火墙访问策略
＃ 客户数量。有两种方法：
＃ （1）运行多个OpenVPN守护进程，每个守护进程一个
＃ group，防火墙TUN / TAP接口
＃ 适合每个组/守护进程。
＃ （2）（高级）动态创建脚本
＃ 修改防火墙以响应访问
＃ 来自不同的客户。见男人
＃ page以获取有关学习地址脚本的更多信息。
;learn-address ./script

＃ 如果启用，将配置此指令
＃ 所有客户端重定向其默认值
＃ net网关通过VPN，造成
＃ 所有IP流量，如网页浏览和
＃ 和DNS查找通过VPN
＃ （OpenVPN服务器机器可能需要NAT
＃ 或将TUN / TAP接口桥接到互联网
＃ 为了使其正常工作）。
;push "redirect-gateway def1 bypass-dhcp"

＃ 某些特定于Windows的网络设置
＃ 可以推送到客户端，例如DNS
＃ 或WINS服务器地址。警告：
＃ http://openvpn.net/faq.html＃ dhcpcaveats
＃ 以下地址是指公众
＃ openns.com提供的DNS服务器。
;push "dhcp-option DNS 208.67.222.222"
;push "dhcp-option DNS 208.67.220.220"

＃ 取消注释此指令以允许不同
＃ 个客户能够“看到”彼此。
＃ 默认情况下，客户端只能看到服务器。
＃ 强制客户端只看到服务器
＃ 还需要适当的防火墙
＃ server的TUN / TAP接口。
;client-to-client

＃ 如果有多个客户端，则取消注释该指令
＃ 可能使用相同的证书/密钥连接
＃ 文件或通用名称。这是推荐的
＃ 仅用于测试目的。用于生产用途，
＃ 每个客户端都应该有自己的证书/密钥
＃ pair。
＃
＃ 如果您没有生成个人
＃ 每个客户的证书/密钥对，
＃ 每个人都拥有自己独特的“共同名称”，
＃ UNCOMMENT THIS LINE OUT。
;duplicate-cn

＃ keepalive指令导致类似ping
＃ 来回发送的消息
＃ 链接，以便每一方知道何时
＃ 另一边已经下降了。
＃ 每隔10秒Ping一次，假设是远程的
＃ 如果在期间没有收到ping，＃ peer将关闭
＃ 120秒的时间段。
keepalive 10 120

＃ 除了提供的额外安全性
＃ 通过SSL / TLS创建“HMAC防火墙”
＃ 帮助阻止DoS攻击和UDP端口泛滥。
＃
＃ 生成：
＃ openvpn --genkey --secret ta.key
＃
＃ 服务器和每个客户端必须具有
＃ 这个密钥的副本。
＃ 第二个参数应为'0'
＃ 在服务器上，'1'在客户端上。
tls-auth ta.key 0 ＃ 这个文件是秘密的

＃ 选择加密密码。
＃ 必须将此配置项复制到
＃ 客户端配置文件。
＃ 注意v2.4客户端/服务器会自动进行
＃ 在TLS模式下协商AES-256-GCM。
＃ 另请参阅联机帮助页中的ncp-cipher选项
cipher AES-256-CBC

＃ 在VPN链路上启用压缩，然后按
客户端的＃ 选项（仅限v2.4 +，对于早期版本
＃ 版本见下文）
;compress lz4-v2
;push "compress lz4-v2"

＃ 对于与旧客户兼容的压缩，请使用comp-lzo
＃ 如果你在这里启用它，你也必须
＃ 在客户端配置文件中启用它。
;comp-lzo

＃ 最大并发连接数
我们想要允许的＃ 个客户。
;max-clients 100

＃ 减少OpenVPN是一个好主意
初始化后＃ daemon的权限。
＃
＃ 你可以取消注释
＃ 非Windows系统。
;user nobody
;group nobody

＃ persist选项会尽量避免
＃ 重启时访问某些资源
＃ 可能无法访问，因为
＃ 特权降级。
persist-key
persist-tun

＃ 输出显示的短状态文件
＃ 当前连接，截断
＃ 并每分钟重写一次。
status openvpn-status.log

＃ 默认情况下，日志消息将转到syslog（或
＃ 在Windows上，如果作为服务运行，他们会去
＃ “\ Program Files \ OpenVPN \ log”目录）。
＃ 使用log或log-append覆盖此默认值。
＃ “log”将截断OpenVPN启动时的日志文件，
＃ 而“log-append”将附加到它。 用一个
＃ 或其他（但不是两者）。
;log         openvpn.log
;log-append  openvpn.log

＃ 设置适当的日志级别
＃  文件冗长。
＃
＃  0 是静默的除了致命错误外，
＃  4 适用于一般用途
＃ 5和6可以帮助调试连接问题
＃  9 非常冗长
verb 3

＃ Silence重复消息。 最多20个
＃ 相同消息的顺序消息
＃ category将输出到日志。
;mute 20

＃ 通知客户端服务器重新启动时如此
＃ 可以自动重新连接。
explicit-exit-notify 1
```

# client.ovpn详解

```ini
##############################################
＃ 示例客户端OpenVPN 2.0配置文件
＃ 用于连接多客户端服务器。
＃
＃ 这个配置可以被多个使用
＃ 客户端，但每个客户端应该
＃ 自己的证书和密钥文件。
＃
＃ 在Windows上，您可能想要重命名此
＃ file所以它有一个.ovpn扩展名
##############################################

＃ 指定我们是客户，我们
＃ 将拉动某些配置文件指令
＃ 来自服务器。
client

＃ 使用与您相同的设置
＃ 服务器。
＃ 在大多数系统上，VPN不起作用
＃ 除非您部分或完全禁用
＃ TUN/TAP接口的防火墙。
;dev tap
dev tun

＃ Windows需要TAP-Win32适配器名称
＃ 来自“网络连接”面板
＃ 如果你有多个。在XP SP2上，
＃ 您可能需要禁用防火墙
＃ 为TAP适配器。
;dev-node MyTap

＃ 我们是否连接到TCP或
＃ UDP服务器？使用相同的设置
＃ 在服务器上。
;proto tcp
proto udp

＃ 服务器的主机名/ IP和端口。
＃ 您可以拥有多个远程条目
＃ 在服务器之间进行负载平衡。
remote my-server-1 1194
;remote my-server-2 1194

＃ 从遥控器中选择一个随机主机
＃ list for load-balancing。除此以外
＃ 按指定的顺序尝试主机。
;remote-random

＃ 继续尝试无限期地解决问题
＃ OpenVPN服务器的主机名。很有用
＃ 在未永久连接的机器上
＃ 到互联网，如笔记本电脑。
resolv-retry infinite

＃ 大多数客户端不需要绑定
＃ 一个特定的本地端口号。
nobind

＃ 初始化后降级权限（仅限非Windows）
;user nobody
;group nobody

＃尝试在重新启动时保留一些状态。
persist-key
persist-tun

＃ 如果你通过连接
＃ HTTP代理到达实际的OpenVPN
＃ server，把代理服务器/ IP和
＃ 端口号在这里。请参见手册页
＃ 如果您的代理服务器需要
＃ 认证
;http-proxy-retry # retry on connection failures
;http-proxy [proxy server] [proxy port #]

＃ 无线网络经常产生很多
＃ 重复数据包的数量。设置此标志
＃ 沉默重复数据包警告。
;mute-replay-warnings

＃ SSL / TLS parms。
＃ 查看服务器配置文件以获取更多信息
＃ description。最好用
＃ 一个单独的.crt / .key文件对
＃ 为每个客户。一个单一的ca.
＃ file可用于所有客户端。
ca ca.crt
cert client.crt
key client.key

＃ 通过检查确认服务器证书
＃ ceceicate具有正确的密钥用法集。
＃ 这是防范的重要预防措施
＃ 这里讨论的潜在攻击：
＃ http://openvpn.net/howto.html#mitm
＃
＃ 要使用此功能，您需要生成
＃ 将您的服务器证书设置为keyUsage
＃ digitalSignature，keyEncipherment
＃ 和extendedKeyUsage到
＃ serverAuth
＃ EasyRSA可以为您做到这一点。
remote-cert-tls server

＃ 如果服务器上使用了tls-auth密钥
＃ 然后每个客户端也必须有密钥。
tls-auth ta.key 1

＃ 选择加密密码。
＃ 如果服务器上使用了密码选项
＃ 然后你还必须在这里指定它。
＃ 注意v2.4客户端/服务器会自动进行
＃ 在TLS模式下协商AES-256-GCM。
＃ 另请参阅联机帮助页中的ncp-cipher选项
cipher AES-256-CBC

＃ 在VPN链路上启用压缩功能。
＃ 除非它也是，否则不要启用它
＃ 在服务器配置文件中启用。
＃ COMP-LZO

＃ 设置日志文件详细程度。
verb 3

＃ Silence重复消息
;mute 20
```
