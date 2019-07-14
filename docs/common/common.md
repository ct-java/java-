[toc]
# 必备知识
## C/S和B/S
## C/S架构
### 概念
- C/S 架构是一种典型的两层架构，其全程是Client/Server，即客户端服务器端架构，其客户端包含一个或多个在用户的电脑上运行的程序，而服务器端有两种，一种是数据库服务器端，客户端通过数据库连接访问服务器端的数据；另一种是Socket服务器端，服务器端的程序通过Socket与客户端的程序通信。
- C/S架构也可以看做是胖客户端架构。因为客户端需要实现绝大多数的业务逻辑和界面展示。这种架构中，作为客户端的部分需要承受很大的压力，因为显示逻辑和事务处理都包含在其中，通过与数据库的交互（通常是SQL或存储过程的实现）来达到持久化数据，以此满足实际项目的需要
### 优点
1. C/S架构的界面和操作可以很丰富
2. 安全性能可以很容易保证，实现多层认证也不难
3.  由于只有一层交互，因此响应速度较快
### 缺点
1. 适用面窄，通常用于局域网中
2. 用户群固定。由于程序需要安装才可使用，因此不适合面向一些不可知的用户
3. 维护成本高，发生一次升级，则所有客户端的程序都需要改变
## B/S架构
### 概念
- B/S架构的全称为Browser/Server，即浏览器/服务器结构。Browser指的是Web浏览器，极少数事务逻辑在前端实现，但主要事务逻辑在服务器端实现，Browser客户端，WebApp服务器端和DB端构成所谓的三层架构。B/S架构的系统无须特别安装，只有Web浏览器即可。
- B/S架构中，显示逻辑交给了Web浏览器，事务处理逻辑在放在了WebApp上，这样就避免了庞大的胖客户端，减少了客户端的压力。因为客户端包含的逻辑很少，因此也被成为瘦客户端
### 优点
1. 客户端无需安装，有Web浏览器即可
2. BS架构可以直接放在广域网上，通过一定的权限控制实现多客户访问的目的，交互性较强
3. BS架构无需升级多个客户端，升级服务器即可
### 缺点
1. 在跨浏览器上，BS架构不尽如人意
2. 表现要达到CS程序的程度需要花费不少精力
3. 在速度和安全性上需要花费巨大的设计成本，这是BS架构的最大问题
4. 客户端服务器端的交互是请求-响应模式，通常需要刷新页面，这并不是客户乐意看到的。（在Ajax风行后此问题得到了一定程度的缓解）
---
## 电子商务模式
### P2P
- P2P借贷是一种将非常小额度的资金聚集起来借贷给有资金需求人群的一种民间小额借贷模式。P2P是“Peer-to-Peer”的简写，个人对个人的意思，P2P借贷指个人通过第三方平台(P2P公司)在收取一定服务费用的前提下向其他个人提供小额借贷的金融模式
- P2P模式有两种模式，第一种是纯线上模式，是纯粹的P2P，在这种平台模式上纯粹进行信息匹配，帮助资金借贷双方更好的进行资金匹配，但缺点明显，这种线上模式并不参与担保；第二种是债权转让模式，平台本身先行放贷，再将债权放到平台进行转让，很明显能让企业提高融资端的工作效率，但容易出现资金池，不能让资金充分发挥效益

### O2O
- O2O即Online To Offline，即将线下商务的机会与互联网结合在了一起，让互联网成为线下交易的前台。这样线下服务就可以用线上来揽客，消费者可以用线上来筛选服务，还有成交可以在线上结算，很快达到规模。该模式最重要的特点是：推广效果可查，每笔交易可跟踪
- O2O营销模式的核心是在线预付，在线支付不仅是支付本身的完成，是某次消费得以最终形成的唯一标志，更是消费数据唯一可靠的考核标准。其是对提供online服务的互联网专业公司而言，只有用户在线上完成支付，自身才可能从中获得效益

### B2C
- B2C是Business-to-Customer的缩写，而其中文简称为“商对客”。“商对客”是电子商务的一种模式，也就是通常说的商业零售，直接面向消费者销售产品和服务。这种形式的电子商务一般以网络零售业为主，主要借助于互联网开展在线销售活动。B2C即企业通过互联网为消费者提供一个新型的购物环境——网上商店，消费者通过网络在网上购物、在网上支付
- B2C电子商务网站由三个基本部分组成：为顾客提供在线购物场所的商场网站；负责为客户所购商品进行配送的配送系统；负责顾客身份的确认及货款结算的银行及认证系统
- 代表网站：天猫——为人服务做平台；京东——自主经营卖产品

### B2B
- B2B，Business-to-Business，企业对企业的电子商务模式，也有写成BTB，是指企业对企业之间的营销关系，它将企业内部网，通过B2B网站与客户紧密结合起来，通过网络的快速反应，为客户提供更好的服务，从而促进企业的业务发展
- B2B是指进行电子商务交易的供需双方都是商家(或企业、公司)，她(他)们使用了互联网的技术或各种商务网络平台，完成商务交易的过程。电子商务是现代B2Bmarketing的一种具体主要的表现形式。
阿里巴巴是国内也是全球最大的B2B电子商务网站。是中小企业首选的B2B平台，主要提供“诚信通”服务，但由于所有用户基本上都是“诚信通”客户。所以没有专业的电子商务运营能力和做阿里巴巴的其它推广业务，很难取得显著效果

### C2C
- C2C实际是电子商务的专业用语，是个人与个人之间的电子商务。C指的是消费者，因为消费者的英文单词是Customer(Consumer)，所以简写为C，而C2C即 Customer(Consumer) to Customer(Consumer)
- 代表网站： 淘宝网；易趣网；拍拍网。无疑问，淘宝在C2C领域的领先地位暂时还没有人能够撼动。然而，淘宝却也不得不承受这份领先带来的沉甸甸压力。在领先与压力之间，淘宝在奋力往前走

### P2C
- P2C即Production to Consumer简称为商品和顾客，产品从生产企业直接送到消费者手中，中间没有任何的交易环节。是继B2B、B2C、C2C之后的又一个电子商务新概念。在国内叫做：生活服务平台
- P2C具体表现为：如果哪天家乐福、沃尔玛、大中电器等这些零售业巨头也进军电子商务，通过互联网开展商务活动，这种商务活动的可能性一直是存在的，并且随着互联网技术的平台发展，还会向中小企业逐步渗透
- 就如大润发超市的飞牛网。P2C把老百姓日常生活当中的一切密切相关的服务信息，如房产、餐饮、交友、家政服务、票务、健康、医疗、保健等聚合在平台上，实现服务业的电子商务化
### 更多模式
- B2C(经济组织对消费者)
- B2B2C(企业对企业对消费者)
- C2B(T)(消费者集合竞价-团购)
- C2C(消费者对消费者)
- B2F(企业对家庭)
- O2O(网上与网下相结合)
- SaaS(软件服务)
- PaaS(平台服务)
- IaaS(基础服务)
- M-B(移动电子商务)
- B2G(政府采购)
- G2B(政府抛售)
- B2M(面向市场营销商务)
- M2C(生产厂商对消费者)
- BAB(企业-联盟-企业)
- P2C(生活服务平台)
- ABC(代理商-商家-消费者)
- P2P(点对点、渠道对渠道)
- SNS-EC(社会化网络)
- B2S(分享式体验式商务)
- B2B(经济组织对经济组织)
- B2C(经济组织对消费者)
- B2B2C(企业对企业对消费者)
- C2B(T)(消费者集合竞价团购)
- C2C(消费者对消费者)
- B2F(企业对家庭)
- SoLoMo(社交本地化移动)
- O2O(网上与网下相结合)
- SaaS(软件服务)
- PaaS(平台服务)
- IaaS(基础服务)
- M-B(移动电子商务)
- B2G(政府采购)
- G2B(政府抛售)
## Cookie篇
### cookie的作用域
1. 在webapp_a下面设置的cookie，在webapp_b下面获取不到，因为cookie的作用域默认是产生cookie的应用的路径
2. 若在webapp_a下面设置cookie的时候，增加一条cookie.setPath("/");或者cookie.setPath("/webapp_b/")；就可以在webapp_b下面获取到设置的cookie了
3. cookie.setPath这个参数;是相对于应用服务器存放应用的文件夹的根目录而言的(比如服务器下面的webapp)，因此cookie.setPath("/")之后，可以在webapp文件夹下的所有应用共享cookie，而cookie.setPath("/webapp_b/")是指应用设置的cookie只能在webapp_b应用下的获得，即便是产生这个cookie的webapp_a应用也不可以
4. 设置cookie.setPath("/webapp_b/jsp")或者cookie.setPath("/webapp_b/jsp/")的时候，只有在webapp_b/jsp文件夹下面可以获得cookie，在webapp_b下面但是在jsp文件夹外的都不能获得cookie
5. 设置cookie.setPath("/webapp_b");，是指在webapp_b下面才可以使用cookie，这样就不可以在产生cookie的应用webapp_a下面获取cookie了
6. 有多条cookie.setPath("XXX");语句的时候，起作用的以最后一条为准
所以解决方法就是：在设置cookie的时候加个path="/"上去
### 设置cookie
```java
//设置cookie时间以秒为单位
function setCookie(c_name,value,expireseconds){
    var exdate=new Date();
    exdate.setTime(exdate.getTime()+expireseconds * 1000);
    document.cookie=c_name+ "=" +escape(value)+
    ((expireseconds==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
}
```
### 删除cookie
```java
//删除cookie
function delCookie(key){
    var date = new Date(); //获取当前时间
    date.setTime(date.getTime()-10000); //将date设置为过去的时间
    document.cookie = key + "=v; expires =" +date.toGMTString()+";path=/";	//设置cookie
 }
```
### 获取cookie代码
```java
//获取cookie
function getCookie(c_name){
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1){
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1){
                c_end=document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start,c_end));
        }
    }
    return "";
}
```
### 跨域请求及跨域携带Cookie解决方案
#### 服务器端使用CROS协议解决跨域访问数据问题时
```Java
//允许跨域的域名，*号为允许所有,存在被 DDoS攻击的可能。
getResponse().setHeader("Access-Control-Allow-Origin","*");
//表明服务器支持的所有头信息字段
getResponse().setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma,Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
/** 目前测试来看为了兼容所有请求方式，上面2个必须设 **/
//如果需要把Cookie发到服务端，需要指定Access-Control-Allow-Credentials字段为true;
getResponse().setHeader("Access-Control-Allow-Credentials", "true");
// 首部字段 Access-Control-Allow-Methods 表明服务器允许客户端使用 POST, GET 和 OPTIONS 方法发起请求。
//该字段与 HTTP/1.1 Allow: response header 类似，但仅限于在需要访问控制的场景中使用。
getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//表明该响应的有效时间为 86400 秒，也就是24小时。在有效时间内，浏览器无须为同一请求再次发起预检请求。
//请注意，浏览器自身维护了一个最大有效时间，如果该首部字段的值超过了最大有效时间，将不会生效。
getResponse().setHeader("Access-Control-Max-Age", "86400");
// IE8 引入XDomainRequest跨站数据获取功能,也就是说为了兼容IE
getResponse().setHeader("XDomainRequestAllowed","1");
```
#### 前端根据实际情况修改发起请求的ajax
- 对于jQuery的Ajax
```Java
$.ajax({
    type: "POST",
    url: "实际的请求地址",
    data: {参数：参数值},
    dataType: "json",
    crossDomain:true, //设置跨域为true
    xhrFields: {
        withCredentials: true //默认情况下，标准的跨域请求是不会发送cookie的
    },
    success: function(data){
        alert("请求成功");      
    }
});
```
- 对于XMLHttpRequest的Ajax请求
```Java
var xhr = new XMLHttpRequest();
xhr.open('GET', url);
xhr.withCredentials = true; // 携带跨域cookie
xhr.send();
```
- 对于axios的Ajax请求
```Java
axios.defaults.withCredentials=true; // 让ajax携带cookie
```
# 顶级域名、一级域名、二级域名
## 域名
可分三级，一级域名，二级域名，三级域名。是由一串字符+域名后缀组成，我们通常说的网址就包含域名
### 根域
最高层的域是根域(root) "."，就是一个点
### 顶级域名/一级域名
- 根域下一层就是顶级域，英语：Top-level domains，first-level domains（TLDs），也翻译为国际顶级域名,也就是一级域名
- 它保存于DNS根域的名字空间中
- 顶级域名是域名的最后一个部分，即是域名最后一点之后的字母，比如 .com .org .cn

#### 顶级域名分为四类
1. 通用顶级域（英语：Generic top-level domain，缩写为gTLD），如".com"、".net"、".org"、".edu"、".info"等，均由国外公司负责管理
2. 国家及地区顶级域（英语：Country code top-level domain，缩写为ccTLD），如".cn"代表中国，".uk"代表英国等，地理顶级域名一般由各个国家或地区
3. 负责管理基础建设顶级域（.arpa，过去曾包括在“通用顶级域”内）
4. 测试顶级域
> N级域名就是在N-1级域名前追加一级
比如二级域名是在一级域名前加一级，二级域名示例：baidu.com zhihu.com qq.com


### 二级域名
- 一级域名下一层就是二级域名
- 就是最靠近顶级域名左侧的字段。如：http://zh.wikipedia.org中，wikipedia就是二级域名(有资料认为, 在顶级域名后面, 还存在一级域名, 那么zh就是二级域名)

### 三级域名
二级域名的子域名，特征是包含三个“.”，一般来说三级域名都是免费的。
一般来说，顶级域名的PR值比二级域名的高，如果一级域名不存在了，二级、三级域名也就不复存在了。
那么,为什么有些时候我们直接在地址栏输入一个IP地址也可以跳转到页面呢?这就是接下来要说的域名和IP之间的关系.
因为在网络上机器彼此连接只能互相识别IP，而数字标识较难记忆，所以才演化出域名来代替IP地址，当我们将在地址栏输入域名欲跳转到某个页面时，点击提交后会由专门的域名解析服务器（DNS服务器）对我们的域名进行解析，得出域名对应的IP地址再进行连接。所以如果我们直接在地址栏输入与域名对应的IP也可以跳转到同一个页面

## 类别域名和行政域名
我国在国际互联网络信息中心（Inter NIC）正式注册并运行的顶级域名是.cn，这也是我国的一级域名。在顶级域名之下，我国的二级域名又分为类别域名和行政区域名两类
### 类别域名
1. 包括用于科研机构的.ac
2. 用于工商金融企业的.com
3. 用于教育机构的.edu
4. ；用于政府部门的.gov
5. 用于互联网络信息中心和运行中心的.net
6. 用于非盈利组织的.org
### 行政域名
行政区域名有34个，分别对应于我国各省、自治区和直辖市
## 父域名与子域名
![fz域名](https://img-blog.csdn.net/20180208232138386)
