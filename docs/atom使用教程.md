---
title: "test"
author: ct
date: 2019/3/30
output:
  pdf_document
    path:/export/test.pdf
---

# markdown使用教程

[TOC]

## 插件篇

### 最强视图插件：Markdown-Preview-Enhanced

### 语法说明

#### 标题

## 

    # 这是以及标题
    ## 这是二级标题
    ### 这是三级标题
    .....

#### 强调

    *这会是斜体的文字*
    _这会是斜体的文字*
    **这会是粗体的文字**
    __这会是粗体的文字__
    ~~这些文字将会被横线删除~~

_这会是斜体的文字_
\_这会是斜体的文字\*
**这会是粗体的文字**
**这会是粗体的文字**
~~这些文字将会被横线删除~~

#### 列表

##### 无序列表

    * Item1
    * Item2
      * Item2a
      * Item2a

-   Item1
-   Item2
    -   Item2a
    -   Item2b
        ##### 有序列表


    1. Item 1
    1. Item 2
    1. Item 3
       1. Item 3a
       1. Item 3b

1.  Item 1
2.  Item 2
3.  Item 3
    1.  Item 3a
    2.  Item 3b
        #### 添加图片


    ![GitHub Logo](/images/logo.png)
    Format: ![Alt Text](url)

![赵敏](https://b-ssl.duitang.com/uploads/item/201808/28/20180828230234_oyfjl.thumb.700_0.jpg)

#### 链接

    [百度一下](http://www.baidu.com)

[百度一下](http://www.baidu.com)

#### 引用

    > 世界上本没有路，走的人多了，也就形成了路！！！

> 世界上本没有路，走的人多了，也就形成了路！！！
>
> #### 分割线
>
>     ---
>     上 连字符
>     ***
>     上 星号
>     ___
>     上 下划线
>
> * * *
>
> 上 连字符
>
> * * *
>
> 上 星号
>
> * * *
>
> 上 下划线
>
> #### 行类代码
>
>     我觉得你应该在这里使用
>     `<addr>` 才对。
>
> 我觉得你应该在这里使用
> `<addr>` 才对。
>
> #### 代码块
>
>     可以用```这个区域就是代码块，需顶格```
>
> #### 代码块class
>
> ```javascript {.class1 .class}
> function add(x, y) {
>   return x + y
> }
> ```
>
> ```javascript
> function add(x, y) {
>   return x + y
> }
> ```
>
> #### 添加行数
>
> ```javascript {.line-numbers}
> function add(x, y) {
>   return x + y
> }
> ```
>
> #### 任务列表
>
>     - [x] @mentions, #refs, [links](), **formatting**, and <del>tags</del> supported
>     - [x] list syntax required (any unordered or ordered list supported)
>     - [x] this is a complete item
>     - [ ] this is an incomplete item
>
> -   [x] @mentions, #refs, [links](<>), **formatting**, and <del>tags</del> supported
> -   [x] list syntax required (any unordered or ordered list supported)
> -   [x] this is a complete item
> -   [ ] this is an incomplete item
>     #### 表格【插件：markdown-table-editor】
>     | 姓名 | 年龄 | 学校     |
>     \| ---- \| ---- \| -------- \|
>     | 张三 | 23   | 四川大学 |
>     | 李四 | 45   | 北京大学 |
>     | 王五 | 64   | 清华大学 |
>     ##### 扩展语法 >
>     | a   | b   |
>     \| --- \| --- \|
>     | >   | 1   |
>     | 2   |     |
>     ##### > 和 ^
>     | a   | b   |
>     \| --- \| --- \|
>     | 1   | 2   |
>     | ^   | 4   |
>     | 5   | 6   |
>     | ^   | 7   |
>     | >   | 9   |
>     #### Emoji & Font-Awesome
>     只适用于 markdown-it parser 而不适用于 pandoc parser。
>     缺省下是启用的。你可以在插件设置里禁用此功能。

:smile:

    :smile:
    :fa-car:

:smile:
:fa-car:

#### 上标

    30^5^

30^5^

#### 下标

    H~2~0

H~2~0

#### 脚注

    狭义相对论 [^1]
    [^1]: 狭义相对论是爱因斯坦的成名著

狭义相对论 [^1]

```java
/**
 * java代码
 * 注：这里用了文档注释插件：docblockr
 */
public class Demo{
  public static void main(String[] args){
    System.out.print("请关注我，谢谢！！！");
  }
}
```

#### 缩略

    *[HTML]: Hyper Text Markup Language
    The HTML Sepcification

\*[HTML]&#x3A; Hyper Text Markup Language
The HTML Sepcification

#### 标记

### 插件 markdown-writer

> CriticMarkup 缺省是禁用的，你可以通过插件设置来启动它。
> 有关 CriticMarkup 的更多信息，请查看 [CriticMarkup](http://criticmarkup.com/users-guide.php) 用户指南.
>
> ##### 高亮
>
> == 我爱你 ==
>
>     添加 {++ ++}
>     删除 {-- --}
>     替换 {~~ ~> ~~}
>     注释 {>> <<}
>     高亮 {== ==}{>> <<}
>
> \++ 添加 ++
> \-- 删除 --
> \~~ 替换 \~~
>
> > 注释 &lt;&lt;
> > == 高亮 ==
> >
> > ## 数学
> >
> > Markdown Preview Enhanced 使用 [KaTeX](https://github.com/Khan/KaTeX) 或者 [MathJax](https://github.com/mathjax/MathJax) 来渲染数学表达式。
> > KaTeX 拥有比 MathJax 更快的性能，但是它却少了很多 MathJax 拥有的特性。你可以查看 KaTeX supported functions/symbols 来了解 KaTeX 支持那些符号和函数。
> >
> >     $...$ 或者 \{...} 中的数学表达式将会在行内显示
> >     $$...$ $或者 \[...\] 或者 ```math中的数学表达式将会在行内显示
> >
> > ## 图像
> >
> > Markdown Preview Enhanced 内部支持 flow charts, sequence diagrams, mermaid, PlantUML, WaveDrom, GraphViz，Vega & Vega-lite，Ditaa 图像渲染。 你也可以通过使用 Code Chunk 来渲染 TikZ, Python Matplotlib, Plotly 等图像。
> >
> > ### Flow Charts
> >
> > 这一特性基于[flowchart.js](http://flowchart.js.org/)
> >
> > -   fow代码块中的内容将会被flowchart.js渲染
> >     ### Sequence diagrams
> >     这一特性基于[js-sequence-diagrams](https://bramp.github.io/js-sequence-diagrams/)
> > -   sequence代码块中的内容将会被js-sequence-diagrams渲染
> > -   支持两个主题 simple (默认主题)和 hand
> >     ### mermaid
> >     Markdown Preview Enhanced 使用[mermaid](https://github.com/knsv/mermaid)来渲染流程图和时序图
> > -   mermaid 代码块中的内容将会渲染 mermaid 图像
> >     三个mermaid主题是支持的，并且可以在插件设置中设置
> > -   mermaid.css
> > -   mermaid.dark.css
> > -   mermaid.forest.css
> >     你还可以通过 Markdown Preview Enhanced: Open Mermaid Config 命令打开 mermaid 配置文件。
> >     ### PlantUML
> >     Markdown preview Enhanced使用[PlantUML](http://plantuml.com/zh/)来创建各种图形。(java是需要先被安装好的)
> > -   你可以安装[GraphViz](http://www.graphviz.org/)来辅助生成各种各样的图形
> > -   puml 或者 plantuml 代码块中的内容会被 PlantUML渲染
> >     如果代码中 @start... 没有被找到，那么 @startuml ... @enduml 将会被自动添加。
> >     ### WaveDrom
> >     Markdown Preview Enhanced使用[WaveDrom](http://wavedrom.com/)来渲染 digital timing diagram.
> >     ### GraphViz
> >     Markdown Preview Enhanced 使用 [Viz.js](https://github.com/mdaines/viz.js) 来渲染 [dot](https://en.wikipedia.org/wiki/DOT_(graph_description_language)) 语言 图形。
> > -   你可以通过 {engine="..."} 来选择不同的渲染引擎。 引擎 circo，dot，neato，osage，或者 twopi 是被支持的。默认下，使用 dot 引擎。
> >     ### Vega和Vega-lite

图像
Vega 和 Vega-lite
Markdown Preview Enhanced 支持[vega](https://vega.github.io/vega/)以及[vega_lite](https://vega.github.io/vega-lite/)的静态图像

-   你也可以 @import 一个 JSON 或者 YAML 文件作为 vega 图像，例如：


    @import "your_vega_source.json" {as="vega"}
    @import "your_vega_lite_source.json" {as="vega-lite"}

### graph_description_language

Markdown Preview Enhanced 支持[ditaa](https://github.com/stathissideris/ditaa)。
(Java 是需要先被安装好的)
ditaa 整合于[code chunk](https://shd101wyy.github.io/markdown-preview-enhanced/#/zh-cn/code-chunk), for example:

[^1]: 狭义相对论是爱因斯坦的成名著
