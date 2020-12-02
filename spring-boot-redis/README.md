
## docker 搭建redis
```
docker run -d --name redis-server -p 6379:6379 -v /Users/admin/Documents/software/docker_volume_mapping/redis/redis_conf:/etc/redis/redis.conf -v /Users/admin/Documents/software/docker_volume_mapping/redis/redis_data:/data redis:latest /etc/redis/redis.conf  --appendonly yes --requirepass "redis"
```