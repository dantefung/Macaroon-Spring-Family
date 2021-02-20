### 一、课程方式安装Docker

这个是我自己做的，源文件什么都没有

```shell
# 1、yum 包更新到最新 
yum update 
# 2、安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的 
yum install -y yum-utils device-mapper-persistent-data lvm2
# 3、 设置yum源
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 4、 安装docker，出现输入的界面都按 y 
yum install -y docker-ce
# 5、 查看docker版本，验证是否验证成功
docker -v
#6. 配置镜像加速器，去阿里云找docker节点
-----------------------------------docker服务命令------------------------------
# 6.开启Docker服务
sudo yum makecache fast
sudo service docker start
systemctl start docker
#7. 停止docker服务
systemctl stop docker
#8.查看docker服务状态
systemctl status docker           dead  |  running
#9.开机启动docker
systemctl enable docker
----------------------------------docker镜像命令-------------------------------
#10.查看镜像
docker images
docker images -q 查看所有镜像的id
#11.搜索镜像
docker search *
#12.拉去镜像
docker pull redis：5.0         不指定则下载最新版
去网站寻找对应镜像有哪些版本号https://hub.docker.com/
#13.删除镜像
docker rmi 镜像+版本号 | image id   删除指定镜像
docker rmi $(docker images -q)	删除所有镜像
-------------------------------docker容器命令--------------------------------
#14. 查看容器
docker ps
docker ps -a
docker ps -aq 查看所有容器id

#15. 创建容器
docker run -it --name=c6 centos:7 /bin/bash   创建交互式容器，退出即关闭
docker run -id --name c9 centos:7     创建守护式容器，后台自动运行
#16. 进入容器
docker exec -it c9 /bin/bash     容器必须在启动状态
#17. 启动容器
docker start 容器名
#18. 停止容器
docker stop c99
#19. 删除容器
docker rm 容器名
docker rm $(docker ps -aq) 删除所有停止运行 的容器
#20. 查看容器信息
```

