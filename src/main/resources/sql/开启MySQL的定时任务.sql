-- 开启定时任务
-- 方法1 修改mysql配置文件
-- my.ini 文件中添加 event_scheduler=1
-- 方法2 执行语句，MySQL重启后需要再次执行
set global event_scheduler = 1;
-- 方法3 执行语句，MySQL8.0
set persist event_scheduler = 1;
show variables like 'event_scheduler';

-- 查看定时任务
show events;

-- 创建定时任务 每天23点，执行存储过程 proc_create_partition('test', null)
drop event if exists `create_partion_event`;
create event `create_partion_event` on schedule every 1 day starts '2020-01-01 23:00:00' on completion preserve enable do call proc_create_partition('test', null);