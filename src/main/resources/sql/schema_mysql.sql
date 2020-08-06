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
  `speed` TINYINT UNSIGNED NOT NULL COMMENT '速度',
  `direction` SMALLINT NOT NULL COMMENT '方向',
  `device_date` DATE GENERATED ALWAYS AS (DATE(`device_time`)) VIRTUAL NOT NULL COMMENT '设备日期',
  `map_fence_id` SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '围栏ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_date_plateno(`device_date`, `plate_no`),
  INDEX idx_plateno_time(`plate_no`, `device_time`),
  INDEX idx_time_deviceid(`device_id`, `device_time`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT '位置数据';

CREATE TABLE `device`  (
  `device_no` VARCHAR(30) NOT NULL COMMENT '设备号',
  `sim_no` VARCHAR(20) NULL COMMENT 'sim卡号',
  `imei` VARCHAR(15) NULL COMMENT 'IMEI号',
  `state` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `device_time` DATETIME COMMENT '设备时间',

  `bind` TINYINT(1) NOT NULL DEFAULT FALSE COMMENT '绑定标志',
  `deleted` TINYINT(1) NOT NULL DEFAULT FALSE COMMENT '删除标志',
  `protocol_version` TINYINT UNSIGNED COMMENT '协议版本号',
  `software_version` VARCHAR(20) COMMENT '软件版本号',
  `hardware_version` VARCHAR(20) COMMENT '硬件版本号',
  `device_model` VARCHAR(30) COMMENT '设备型号',
  `maker_id` VARCHAR(11) COMMENT '制造商',
  `updater` VARCHAR(32) NOT NULL COMMENT '更新者',
  `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`device_no`)
) ENGINE = InnoDB COMMENT = '设备表';