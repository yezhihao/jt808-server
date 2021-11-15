CREATE TABLE IF NOT EXISTS `location`
(
  `vehicle_id`  MEDIUMINT UNSIGNED NOT NULL COMMENT '车辆ID',
  `device_time` DATETIME           NOT NULL COMMENT '设备时间',
  `mobile_no`   VARCHAR(20)        NOT NULL COMMENT '手机号',
  `device_id`   VARCHAR(30)        NOT NULL COMMENT '设备ID',
  `warn_bit`    INT                NOT NULL COMMENT '报警标志',
  `status_bit`  INT                NOT NULL COMMENT '状态',
  `longitude`   INT                NOT NULL COMMENT 'GPS经度',
  `latitude`    INT                NOT NULL COMMENT 'GPS纬度',
  `altitude`    SMALLINT           NOT NULL COMMENT '海拔(米)',
  `speed`       SMALLINT UNSIGNED  NOT NULL COMMENT '速度(1/10公里每小时)',
  `direction`   SMALLINT           NOT NULL COMMENT '方向',
  `created_at`  DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `device_date` DATE GENERATED ALWAYS AS (DATE(`device_time`)) VIRTUAL NOT NULL COMMENT '设备日期',
  INDEX idx_date_vehicleid (`device_date`, `vehicle_id`),
  INDEX idx_devicetime (`device_time`),
  PRIMARY KEY (`vehicle_id`, `device_time`)
) ENGINE = InnoDB COMMENT '位置数据' PARTITION BY RANGE COLUMNS (device_time)(
  PARTITION 2021_01_01 VALUES LESS THAN ('2021-01-01'),
  PARTITION 2021_01_02 VALUES LESS THAN ('2021-01-02')
);