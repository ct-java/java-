# atom快捷键设置
Atom编辑器支持自定义按键绑定,文件格式是CSON;
## 按键绑定组成
按键绑定 = 快捷键(Keystroke) + 执行命令(Command) + 来源(Source) + 选择器(Selector)
- 快捷键不用解释了
- 执行命令就是按下快捷键所调用的命令
- 来源就是命令来自来源,比如core就是内置核心命令,Atom - beautifier是我安装的插件
- 选择器可以理解为匹配,学过CSS/JQ的一听就懂了
## 按键绑定
官方范例：
```json
'atom-text-editor':
   'enter': 'editor:newline'

 'atom-workspace':
   'ctrl-shift-p': 'core:move-up'
   'ctrl-p': 'core:move-down'
```
## 冲突解决
高级的绑定,比如overlay(覆盖),unset(取消设置值)等,以后再写,有些自己还没玩透!!!
范例：
```json
'.platform-darwin atom-workspace atom-text-editor:not(.mini)' :
  'shift-cmd-C' :'unset!'
```
