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
  INDEX idx_device_time (`device_time`),
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
  INDEX idx_plate_no (`plate_no`),
  INDEX idx_mobile_no (`mobile_no`),
  INDEX idx_vehicle_id (`vehicle_id`),
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
  `updated_by`       VARCHAR(32)        NOT NULL DEFAULT 'db' COMMENT '更新者',
  `created_by`       VARCHAR(32)        NOT NULL DEFAULT 'db' COMMENT '创建者',
  `updated_at`       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX d_idx_vehicle_id (`vehicle_id`),
  UNIQUE d_uni_mobile_no (`mobile_no`),
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
  `updated_by`      VARCHAR(32)       NOT NULL DEFAULT 'db' COMMENT '更新者',
  `created_by`      VARCHAR(32)       NOT NULL DEFAULT 'db' COMMENT '创建者',
  `updated_at`      DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`      DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `area_updated_at` DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '区域更新时间',
  INDEX idx_area_updated_at (`area_updated_at`),
  UNIQUE v_uni_plate_no (`plate_no`),
  UNIQUE v_uni_device_id (`device_id`),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT '车辆表';


CREATE TABLE IF NOT EXISTS `agency`
(
  `id`         SMALLINT UNSIGNED AUTO_INCREMENT COMMENT '机构ID',
  `name`       VARCHAR(20)  NOT NULL COMMENT '机构简称',
  `full_name`  VARCHAR(40)  NOT NULL COMMENT '机构全称',
  `remark`     VARCHAR(255) NULL COMMENT '备注',
  `updated_by` VARCHAR(32)  NOT NULL DEFAULT 'db' COMMENT '更新者',
  `created_by` VARCHAR(32)  NOT NULL DEFAULT 'db' COMMENT '创建者',
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT '机构表';


CREATE TABLE IF NOT EXISTS `area_vehicle`
(
  `vehicle_id` MEDIUMINT UNSIGNED NOT NULL COMMENT '车辆ID',
  `area_id`    SMALLINT UNSIGNED  NOT NULL COMMENT '区域ID',
  `created_by` VARCHAR(32)        NOT NULL DEFAULT 'db' COMMENT '创建者',
  `created_at` DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`vehicle_id`, `area_id`)
) ENGINE = InnoDB COMMENT '区域车辆绑定';


CREATE TABLE IF NOT EXISTS `area`
(
  `id`           SMALLINT UNSIGNED AUTO_INCREMENT COMMENT '区域ID',
  `agency_id`    SMALLINT UNSIGNED NOT NULL COMMENT '机构ID',
  `name`         VARCHAR(30)       NOT NULL COMMENT '名称',
  `area_desc`    VARCHAR(255)      NULL COMMENT '描述',
  `geom_type`    TINYINT UNSIGNED  NOT NULL COMMENT '几何类型: 1.圆形 2.矩形 3.多边形 4.路线',
  `geom_text`    TEXT              NOT NULL COMMENT '几何数据: 圆形[x,y,r] 矩形[x,y,x,y] 多边形[x,y,x,y,x,y] 路线[x,y,x,y,w]',
  `mark_type`    TINYINT UNSIGNED  NOT NULL COMMENT '标记类型: 1.作业区 2.停车场 3.禁行区',
  `limit_in_out` TINYINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '限制出入: 0.无 1.进区域 2.出区域',
  `limit_speed`  TINYINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '限速(公里每小时) 0不限制',
  `limit_time`   TINYINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '限停(分钟) 0不限制',
  `weeks`        TINYINT UNSIGNED  NOT NULL DEFAULT 127 COMMENT '生效日(按位,周一至周日)',
  `start_date`   DATE              NULL COMMENT '开始日期',
  `end_date`     DATE              NULL COMMENT '结束日期',
  `start_time`   TIME              NULL COMMENT '开始时间',
  `end_time`     TIME              NULL COMMENT '结束时间',
  `deleted`      TINYINT(1)        NOT NULL DEFAULT 0 COMMENT '删除标志',
  `updated_by`   VARCHAR(32)       NOT NULL DEFAULT 'db' COMMENT '更新者',
  `created_by`   VARCHAR(32)       NOT NULL DEFAULT 'db' COMMENT '创建者',
  `updated_at`   DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at`   DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT '区域表';

CREATE TABLE IF NOT EXISTS `online_record`
(
  `agency_id`       SMALLINT UNSIGNED  NOT NULL COMMENT '机构ID',
  `vehicle_id`      MEDIUMINT UNSIGNED NOT NULL COMMENT '车辆ID',
  `mobile_no`       VARCHAR(20)        NOT NULL COMMENT '手机号',
  `device_id`       VARCHAR(30)        NOT NULL COMMENT '设备ID',
  `online_time`     DATETIME           NOT NULL COMMENT '上线时间',
  `online_duration` MEDIUMINT UNSIGNED NOT NULL COMMENT '在线时长',
  INDEX idx_online_time (`online_time`),
  PRIMARY KEY (`vehicle_id`, `online_time`)
) ENGINE = InnoDB COMMENT '在线记录';