CREATE TABLE IF NOT EXISTS `location`
(
  `device_time` DATETIME          NOT NULL COMMENT '设备时间',
  `device_id`   VARCHAR(30)       NOT NULL COMMENT '设备号',
  `mobile_no`   VARCHAR(20)       NOT NULL COMMENT '手机号',
  `plate_no`    CHAR(8)           NOT NULL COMMENT '车牌号',
  `warn_bit`    INT               NOT NULL COMMENT '报警标志',
  `status_bit`  INT               NOT NULL COMMENT '状态',
  `longitude`   INT               NOT NULL COMMENT 'GPS经度',
  `latitude`    INT               NOT NULL COMMENT 'GPS纬度',
  `altitude`    SMALLINT          NOT NULL COMMENT '高程(米)',
  `speed`       SMALLINT UNSIGNED NOT NULL COMMENT '速度(1/10公里每小时)',
  `direction`   SMALLINT          NOT NULL COMMENT '方向',
  `alarm_type`  TINYINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '报警类型',
  `create_time` DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `device_date` DATE GENERATED ALWAYS AS (DATE(`device_time`)) VIRTUAL NOT NULL COMMENT '设备日期',
  INDEX idx_date_plateno (`device_date`, `plate_no`),
  INDEX idx_plateno_time (`plate_no`, `device_time`),
  PRIMARY KEY (`device_id`, `device_time`)
) ENGINE = InnoDB COMMENT '位置数据' PARTITION BY RANGE COLUMNS (device_time)(
  PARTITION p_2021_01_01 VALUES LESS THAN ('2021-01-01'),
  PARTITION p_2021_01_02 VALUES LESS THAN ('2021-01-02')
);