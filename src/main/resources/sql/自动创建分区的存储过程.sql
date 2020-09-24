-- 调用存储过程
call proc_create_partition('location', null);
call proc_create_partition('location', '2020-01-07');

-- 删除存储过程
drop procedure if exists `proc_create_partition`;

-- 创建分区的存储过程
-- table_name 分区表的表名
-- p_date 分区的日期 yyyy-MM-dd
CREATE PROCEDURE `proc_create_partition`(IN table_name VARCHAR(30), IN p_date DATE)
BEGIN	
	IF p_date IS NULL THEN
	  -- 如果 p_date为空，找到当前表的最大分区，并且加一天
		select DATE_ADD(REPLACE(p.partition_description,'\'',''), interval 1 day) into p_date
		from information_schema.partitions p
		where p.table_name = table_name
		order by p.partition_ordinal_position desc limit 1;
	END IF;

	-- 创建分区
	set @cmd=concat('alter table ', table_name, ' add partition (partition P_', DATE_FORMAT(p_date,'%Y_%m_%d'), ' values less than (''', p_date, '''))');
	select @cmd;

	prepare stmt from @cmd;
	execute stmt;
	deallocate prepare stmt;
END