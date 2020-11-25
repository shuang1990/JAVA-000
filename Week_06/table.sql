CREATE TABLE `goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '产品名称',
  `goods_weight` decimal(7,3) NOT NULL DEFAULT '0.000' COMMENT '产品重要',
  `goods_number` smallint(6) unsigned NOT NULL COMMENT '库存数量',
  `cate_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分类id',
  `img` varchar(255) NOT NULL DEFAULT '' COMMENT '产品图片连接',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '产品连接',
  `price` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '添加时间',
  `shelf_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上架时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `version` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '最新版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

CREATE TABLE `goods_snapshot` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID,关联goods表的主键ID',
  `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '产品名称',
  `goods_weight` decimal(7,3) NOT NULL DEFAULT '0.000' COMMENT '产品重要',
  `goods_number` smallint(6) unsigned NOT NULL COMMENT '库存数量',
  `cate_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分类id',
  `img` varchar(255) NOT NULL DEFAULT '' COMMENT '产品图片连接',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '产品连接',
  `price` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '添加时间',
  `shelf_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上架时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `version` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '最新版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品快照表';

CREATE TABLE `order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `order_sn` varchar(48) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '订单用户,关联users表主键ID',
  `coupon_code` varchar(64) NOT NULL DEFAULT '' COMMENT '订单使用的coupon_code',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '下单时间时间戳',
  `pay_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '付款时间时间戳',
  `deliver_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发货时间时间戳',
  `order_amount` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额(包含运费,成交费等)',
  `order_money` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `express_fee` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `express_code` varchar(20) NOT NULL DEFAULT '' COMMENT '物流方式编码',
  `depot_code` varchar(20) NOT NULL DEFAULT '' COMMENT '仓库编码',
  `payment_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付方式 0-银行卡，1-支付宝，2-微信',
  `pay_status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '订单支付状态:1-未付款,2-部分付款,3-完全付款,4-超额付款',
  `refund_status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '订单退款状态:1-未退款,2-部分退款,3-完全退款,4-退款失败',
  `order_status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '订单状态:1-新增,2-处理中,3-已取消,4-完结',
  `shipping_status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '订单发货状态:1-未发货,2-部分发货,3-完全发货',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最近更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `order_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID,关联order表主键ID',
  `goods_snapshot_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品快照ID,关联goods_snapshot表主键ID',
  `goods_quantity` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '购买数量',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_goods_snapshot_id` (`goods_snapshot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品表';

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `email` varchar(48) NOT NULL COMMENT '订单编号',
  `password` varchar(48) NOT NULL COMMENT '订单编号',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '头像URL',
  `contact` varchar(64) NOT NULL DEFAULT '' COMMENT '联系方式',
  `sex` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '性别：0|男,1|女,2|保密',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '下单时间时间戳',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户状态:0-待激活,1-已激活,2-已注销,3-已锁定',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最近更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';