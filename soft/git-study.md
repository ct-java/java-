[toc]
# 前提操作
1. 安装好git
2. 必须设置用户名和密码
`git config --global user.name "Your Name"`
`git config --global user.email "email@example.com"`
3. 其他操作（创建本地仓库，clone远程仓库，关联远程仓库...）
# git常规操作
***
- 查看git配置信息
`git config –list`
- 修改git编辑器
`git config --global core.editor vim`
- 修改差异合并工具
`git config --global merge.tool kdiff4或vimdiff`
***
- clone远程库
`git clone <版本库的网址>`
- 查看远程库信息
`git remote -v`
origin    git@gitee.com:liaoxuefeng/learngit.git (fetch)
origin    git@gitee.com:liaoxuefeng/learngit.git (push)
- 移除远程库
`git remote rm origin`
- 绑定远程库[gitee,github]
绑定gitee
`git remote add gitee git@gitee.com:liaoxuefeng/learngit.git`
绑定github
`git remote add github git@github.com:liaoxuefeng/learngit.git`
***
- 从远程库检出
`git fetch <远程主机名>`
- 更新远程库(本地->远程)
`git push <远程主机名> <远程分支名>:<本地分支名>`
- 强制提交,未更新（pull的代码会被覆盖掉 -丢失）
`git push -u origin master -f`
- 远程库检出并与本地合并(远程->本地)
`git pull <远程主机名> <远程分支名>:<本地分支名>`
`git pull`等同于`git fetch`之后`git merge`
***
- 创建远程分支
`git branch 分支名`（先创建本地分支）
- push到远程分支
 `git push origin 本地分支名：远程分支名`（不填写远程分支名，会提交到与本地分支相同的分支上）
- 查看本地分支与远程分支关联情况
`git branch -vv`
- 切换分支(本地分支)
`git checkout jsq2.0_azx`
- 自动切换分支
`git checkout --track origin/master(分支名，同时本地分支也会切换到对应的分支上)`
- 查看本地分支
`git branch(*代表当前分支)`
- 查看远程分支
`git branch -r`
- 查看本地和远程分支情况
`git branch -a`
- 删除分支（不能删除当前分支）
`git branch -D 远程分支名`（D强制删除）
- 合并分支
先切换到主分支master `git checkout master`
然后合并 `git merge jsq2.0_azx`
合并前需push
-----------------------------------------------------------
# git关联github/gitee的两种方式
1. 将远程仓库clone到本地仓库
2. 将本地的代码关联到远程仓库
## 将远程仓库clone到本地仓库(HTTPS|SSH)
### 操作步骤【SSH】
1. 检出你的电脑是否有密钥id_rsa和id_rsa.pub
2. 没有的话，创建 `ssh-keygen -t rsa -b 4096 -C "634802993@qq.com"`
3. 检查你的ssh-agent是否是活跃的 `eval "$(ssh-agent -s)"`
4. 将私钥id_rsa添加到`ssh-add ~/.ssh/id_rsa`
5. 将公钥id_rsa.pub添加到git网站中，如github
6. 测试是否连通：`ssh -T git@gitee.com`
7. 克隆仓库到本地：`git clone git@gitee.com:ct_java/javaInterview`
### 注意事项
- 建议使用SSH，速度快是最大的优势
- 步骤2生成公钥/私钥如果不设置密码一直按回车就可以(建议不设置密码)
- 步骤2填写github上的邮箱
- 步骤7是git@gitee.com表示gitee网站，ct_java表示当前用户，javaInterview表示仓库
- linux系统中：`cd ~/.ssh`
- window系统中：C:\\Users\\Administrator\\.ssh
## 将本地的代码关联到远程仓库
### 操作步骤【SSH】
1. 在github上新建一个仓库
2. 在本地仓库中执行`git remote add gitee git@gitee.com:ct_java/javaInterview`
3. `git push origin maste`r将代码提交到不同的仓库中，可以指定分支
4. `git pull origin master` 先将github上的代码pull下来
5. `git push origin master`
# git更新代码到本地
## 正规流程
1. git status（查看本地分支文件信息，确保更新时不产生冲突）
2. git checkout – [file name] （若文件有修改，可以还原到最初状态; 若文件需要更新到服务器上，应该先merge到服务器，再更新到本地）
3. git branch（查看当前分支情况）
4. git checkout remote branch (若分支为本地分支，则需切换到服务器的远程分支)
5. git pull
## 快速流程
上面是比较安全的做法，如果你可以确定"本地什么都没有改过"
1. git pull (一句命令搞定)
