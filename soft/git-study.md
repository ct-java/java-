# 远程仓库
## gitee,github
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
- 更新远程库
`git pull <远程主机名> <远程分支名>:<本地分支名>`
- 远程库检出并与本地合并
`git pull <远程主机名> <远程分支名>:<本地分支名>`
