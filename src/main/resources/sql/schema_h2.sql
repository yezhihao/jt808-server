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
  `speed` SMALLINT UNSIGNED NOT NULL COMMENT '速度(百米每小时)',
  `direction` SMALLINT NOT NULL COMMENT '方向',
  `map_fence_id` SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '围栏ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_plateno_time(`plate_no`, `device_time`),
  INDEX idx_time_deviceid(`device_id`, `device_time`)
) ENGINE=InnoDB COMMENT '位置数据';

CREATE TABLE IF NOT EXISTS `device`  (
  `device_id` VARCHAR(30) NOT NULL COMMENT '设备号',
  `mobile_no` VARCHAR(20) COMMENT '手机号',
  `plate_no` CHAR(8) NOT NULL DEFAULT '' COMMENT '车牌号',
  `imei` VARCHAR(15) NULL COMMENT 'IMEI号',
  `state` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `online` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0.离线 1.在线',
  `device_time` DATETIME COMMENT '设备时间（离线在线时间）',
  `register_time` DATETIME COMMENT '注册时间（最近注册时间）',
  `install_time` DATETIME COMMENT '安装时间（首次注册时间）',

  `bind` TINYINT(1) NOT NULL DEFAULT FALSE COMMENT '绑定标志',
  `deleted` TINYINT(1) NOT NULL DEFAULT FALSE COMMENT '删除标志',
  `protocol_version` TINYINT UNSIGNED COMMENT '协议版本号',
  `software_version` VARCHAR(20) COMMENT '软件版本号',
  `hardware_version` VARCHAR(20) COMMENT '硬件版本号',
  `device_model` VARCHAR(30) COMMENT '设备型号',
  `maker_id` VARCHAR(11) COMMENT '制造商',
  `city_id` SMALLINT UNSIGNED COMMENT '市县域ID',
  `province_id` TINYINT UNSIGNED COMMENT '省域ID',
  `creator` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE idx_mobileno(`mobile_no`),
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB COMMENT = '设备表';