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
  INDEX idx_devicetime (`device_time`),
  PRIMARY KEY (`vehicle_id`, `device_time`)
) ENGINE = InnoDB COMMENT '位置数据';


CREATE TABLE IF NOT EXISTS `device_status`
(
  `online`      TINYINT(1)         NOT NULL DEFAULT 0 COMMENT '0.离线 1.在线',
  `plate_no`    CHAR(8)            NOT NULL DEFAULT '' COMMENT '车牌号',
  `agency_id`   SMALLINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '机构ID',
  `vehicle_id`  MEDIUMINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '车辆ID',
  `device_time` DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '设备时间',
  `mobile_no`   VARCHAR(20)        NOT NULL DEFAULT '' COMMENT '手机号',
  `device_id`   VARCHAR(30)        NOT NULL DEFAULT '' COMMENT '设备ID',
  `warn_bit`    INT                NOT NULL DEFAULT 0 COMMENT '报警标志',
  `status_bit`  INT                NOT NULL DEFAULT 0 COMMENT '状态',
  `longitude`   INT                NOT NULL DEFAULT 0 COMMENT 'GPS经度',
  `latitude`    INT                NOT NULL DEFAULT 0 COMMENT 'GPS纬度',
  `altitude`    SMALLINT           NOT NULL DEFAULT 0 COMMENT '海拔(米)',
  `speed`       SMALLINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '速度(1/10公里每小时)',
  `direction`   SMALLINT           NOT NULL DEFAULT 0 COMMENT '方向',
  `address`     VARCHAR(255)       NULL COMMENT '当前位置',
  `updated_at`  DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`  DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_plateno (`plate_no`),
  INDEX idx_mobileno (`mobile_no`),
  INDEX idx_vehicleid (`vehicle_id`),
  PRIMARY KEY (`device_id`)
) ENGINE = InnoDB COMMENT '设备状态';


CREATE TABLE IF NOT EXISTS `device`
(
  `device_id`        VARCHAR(30)        NOT NULL COMMENT '设备ID',
  `agency_id`        SMALLINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '机构ID',
  `vehicle_id`       MEDIUMINT UNSIGNED NOT NULL COMMENT '车辆ID',
  `mobile_no`        VARCHAR(20)        NULL COMMENT '手机号',
  `iccid`            CHAR(20)           NULL COMMENT '集成电路卡识别码',
  `imei`             CHAR(15)           NULL COMMENT '国际移动设备识别码',
  `register_time`    DATETIME           NULL COMMENT '注册时间(最近注册时间)',
  `install_time`     DATETIME           NULL COMMENT '安装时间(首次注册时间)',
  `protocol_version` TINYINT            NULL COMMENT '协议版本号',
  `software_version` VARCHAR(20)        NULL COMMENT '软件版本号',
  `firmware_version` VARCHAR(255)       NULL COMMENT '固件版本号',
  `hardware_version` VARCHAR(255)       NULL COMMENT '硬件版本号',
  `device_model`     VARCHAR(30)        NULL COMMENT '设备型号',
  `maker_id`         VARCHAR(11)        NULL COMMENT '制造商',
  `deleted`          TINYINT(1)         NOT NULL DEFAULT 0 COMMENT '删除标志',
  `updated_by`       VARCHAR(32)        NOT NULL COMMENT '更新者',
  `created_by`       VARCHAR(32)        NOT NULL COMMENT '创建者',
  `updated_at`       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX d_idx_vehicleid (`vehicle_id`),
  UNIQUE d_uni_mobileno (`mobile_no`),
  PRIMARY KEY (`device_id`)
) ENGINE = InnoDB COMMENT '设备表';


CREATE TABLE IF NOT EXISTS `vehicle`
(
  `id`              MEDIUMINT UNSIGNED AUTO_INCREMENT COMMENT '车辆ID',
  `agency_id`       SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '机构ID',
  `device_id`       VARCHAR(30)       NULL COMMENT '设备ID',
  `plate_no`        CHAR(8)           NOT NULL COMMENT '车牌号',
  `vin_no`          CHAR(17)          NULL COMMENT '车架号',
  `engine_no`       VARCHAR(17)       NULL COMMENT '引擎号',
  `plate_color`     TINYINT UNSIGNED  NULL COMMENT '车牌颜色: 1.蓝色 2.黄色 3.黑色 4.白色 9.其他',
  `vehicle_type`    TINYINT UNSIGNED  NULL COMMENT '车辆类型',
  `city_id`         SMALLINT UNSIGNED NULL COMMENT '市县域ID',
  `province_id`     TINYINT UNSIGNED  NULL COMMENT '省域ID',
  `remark`          VARCHAR(255)      NULL COMMENT '备注',
  `updated_by`      VARCHAR(32)       NOT NULL COMMENT '更新者',
  `created_by`      VARCHAR(32)       NOT NULL COMMENT '创建者',
  `updated_at`      DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`      DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `area_updated_at` DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '区域更新时间',
  UNIQUE v_uni_plateno (`plate_no`),
  UNIQUE v_uni_deviceid (`device_id`),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT '车辆表';


CREATE TABLE IF NOT EXISTS `agency`
(
  `id`         SMALLINT UNSIGNED AUTO_INCREMENT COMMENT '机构ID',
  `name`       VARCHAR(20)  NOT NULL COMMENT '机构简称',
  `full_name`  VARCHAR(40)  NOT NULL COMMENT '机构全称',
  `remark`     VARCHAR(255) NULL COMMENT '备注',
  `updated_by` VARCHAR(32)  NOT NULL COMMENT '更新者',
  `created_by` VARCHAR(32)  NOT NULL COMMENT '创建者',
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT '机构表';