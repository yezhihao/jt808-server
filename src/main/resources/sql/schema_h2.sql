CREATE TABLE IF NOT EXISTS location (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_time` DATETIME NOT NULL COMMENT '设备时间',
  `device_id` VARCHAR(20) NOT NULL COMMENT '设备号',
  `plate_no` CHAR(8) NOT NULL DEFAULT '' COMMENT '车牌号',
  `warning_mark` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '报警标志',
  `status` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `latitude` INT NOT NULL COMMENT 'GPS地图纬度',
  `longitude` INT NOT NULL COMMENT 'GPS地图经度',
  `altitude` SMALLINT NOT NULL COMMENT '海拔(单位米)',
  `speed` SMALLINT UNSIGNED NOT NULL COMMENT '速度',
  `direction` SMALLINT NOT NULL COMMENT '方向',
  `map_fence_id` SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '围栏ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_plateno_time(`plate_no`, `device_time`),
  INDEX idx_time_deviceid(`device_id`, `device_time`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT '位置数据';