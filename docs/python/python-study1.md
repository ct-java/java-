# python学习第一种
## 写数据到文件
```python
def write_to_file(content):
  with open('result.txt','a') as f:
    f.write('hello world') + '\n'
    f.close()
```
## 字典变字符串
```python
import json
# content是字典数据{‘key’:'value','key2':'value2'}
json.dumps(content)
```
## 字典变成请求参数
```python
data = {
  'username':'不知所云',
  'keyword':'杭州街拍'
}
url = 'http://xxx.xxx.com' + urlencode(data)
```
## requests响应数据乱码
```python
html = requests.get('http://www.baidu.com')
html.encoding = 'utf-8' 
```
## 写字典数据到文件utf-8编码
```python
import json
def write_to_file(content):
  with open('result.txt','a',encoding='utf-8') as f:
    f.write(json.dumps(content,ensure_ascii=False)) + '\n'
    f.close()
```
## 字符串转成json
```python
import json
dataJson = json.loads(html)
```
## 多线程启动
```python
import multiprocessing import Pool
pool = Pool()
Pool.map(main,[i*10 for i in range(10)])
```
