## centos彻底卸载python
- 强制删除已安装python及其关联
rpm -qa|grep python|xargs rpm -ev --allmatches --nodeps
- 删除残余文件
whereis python|xargs rm -frv
