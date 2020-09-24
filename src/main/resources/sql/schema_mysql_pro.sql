CREATE TABLE IF NOT EXISTS `location` (
  `device_time` DATETIME NOT NULL COMMENT '设备时间',
  `device_id` VARCHAR(30) NOT NULL COMMENT '设备号',
  `mobile_no` VARCHAR(20) NOT NULL COMMENT '手机号',
  `plate_no` CHAR(8) NOT NULL DEFAULT '' COMMENT '车牌号',
  `warning_mark` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '报警标志',
  `status` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `latitude` INT NOT NULL COMMENT 'GPS地图纬度',
  `longitude` INT NOT NULL COMMENT 'GPS地图经度',
  `altitude` SMALLINT NOT NULL COMMENT '海拔(单位米)',
  `speed` TINYINT UNSIGNED NOT NULL COMMENT '速度',
  `direction` SMALLINT NOT NULL COMMENT '方向',
  `device_date` DATE GENERATED ALWAYS AS (DATE(`device_time`)) VIRTUAL NOT NULL COMMENT '设备日期',
  `map_fence_id` SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '围栏ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_date_plateno(`device_date`, `plate_no`),
  INDEX idx_plateno_time(`plate_no`, `device_time`),
  PRIMARY KEY (`device_id`, `device_time`)
) ENGINE=InnoDB COMMENT '位置数据' PARTITION BY RANGE COLUMNS(device_time)(
	PARTITION p_2020_01_01 VALUES LESS THAN('2020-01-01'),
	PARTITION p_2020_01_02 VALUES LESS THAN('2020-01-02')
);