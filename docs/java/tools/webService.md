# webservice简介
- Web service是一个平台独立的，低耦合的，自包含的、基于可编程的web的应用程序，可使用开放的XML（标准通用标记语言下的一个子集）标准来描述、发布、发现、协调和配置这些应用程序，用于开发分布式的互操作的应用程序。
# webservice开发规范
- JAVA中共有三种WebService规范，分别是JAXM&SAAJ、JAX-WS（JAX-RPC）、JAX-RS。
## JAX-WS(JAVA API For XML-WebService)
- jdk1.6自带的版本为JAX-WS2.1，其底层支持为JAXB。JAX-WS(JSP 224)规范的API位于javax.xml.ws.\*包，其中大部分都是注解，提供API操作Web服务（通常客户端使用的较多，由于客户端可以借助SDK生成，因此这个包中的API我们较少会直接使用）
## JAXM&SAAJ
### JAXM((JAVA API For XML Message))
- 主要定义了包含了发送和接受消息所需的API，相当于Web服务的服务器端，其API位于javax.messaging.\*包，它是JAVA EE的可选包，因此需要单独下载。
### SAAJ(SOAP With Attachment API For Java,JSR67)
- SAAJ是与JAXM搭配使用的API,为构建SOAP包和解析SOAP包提供了重要的支撑，支持附件传输，他在服务器端、客户端都需要使用。这里还要提到的是SAAJ规范，其API位于javax.xml.soap.\*包。
- JAXM&SAAJ与JAX-WS都是计与SOAP的Web服务，相比之下JAXM&SAAJ暴露了SOAP更多的底层细节，编码比较麻烦，而JAX-WS更加抽象，隐藏了更多的细节，更加面向对象，实现起来你基本上不需要关心
