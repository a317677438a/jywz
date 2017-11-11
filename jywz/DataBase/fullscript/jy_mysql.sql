/*==============================================================*/
/* table: jy_material_type          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_material_type;
CREATE TABLE jy_material_type (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  code varchar(10) NOT NULL unique COMMENT '物资类型编码',
  name varchar(50) NOT NULL COMMENT '物资类型名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资类型表';


/*==============================================================*/
/* table: jy_params          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_params;
CREATE TABLE jy_params (
  param_type varchar(50)  comment '参数类型:organize:组织、storehouse：仓库',
  param_code varchar(50) unique comment '编码',
  param_name varchar(50)  comment '名称',
  PRIMARY KEY (param_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';


/*==============================================================*/
/* table: jy_material         2017/09/24		*/
/*==============================================================*/
drop table if exists jy_material;
CREATE TABLE jy_material (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  jy_material_type_id int(11) NOT NULL  COMMENT '物资类型表系统id',
  code varchar(10) not null unique COMMENT '物资编码',
  name varchar(50) NOT NULL COMMENT '物资名称',
  model varchar(100)  COMMENT '规格型号',
  supplier varchar(100)  COMMENT '供应商（厂家）',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资表';



/*==============================================================*/
/* table: jy_user          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_user;
CREATE TABLE jy_user (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  loginname            varchar(30) not null unique comment '登录账号',
  passwd               varchar(32) not null comment '登录密码，md加密',
  role               int(2) not null comment '1、系统管理员2、仓管员3、领导、4、物资申请人',
  organize_code			 varchar(32) not null comment '1、计量中心 2、电网计算班3、质检班4、计量自动化班5、
  用户计量一班6、用户计量二班7、用户计量三班8、室内检定班9、电能量数据班',
  code               varchar(100) not null unique comment '人员编号',
  name               varchar(100) not null comment '人员名称',
  ophone				varchar(50)  comment '办公电话',
  mphone				varchar(50)  comment '手机',
  status               int(2) not null default '1' comment '状态：1：正常，2：停用',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';


/*==============================================================*/
/* table: jy_user_storehouse        2017/09/24		*/
/*==============================================================*/
drop table if exists jy_user_storehouse;
CREATE TABLE jy_user_storehouse (
  jy_user_id int(11) NOT NULL  COMMENT '用户信息表系统id',
  storehouse_code  varchar(50) NOT NULL unique comment '仓库编码',
  PRIMARY KEY (jy_user_id,storehouse_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理仓库对应关系表';



/*==============================================================*/
/* table: jy_storehouse_in          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_storehouse_in;
CREATE TABLE jy_storehouse_in (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  putin_code   varchar(50) NOT NULL unique comment '入库单号',
  putin_type   int(2) not null comment '入库类型：1、采购入库，2、退库入库，3、移库入库',
  putin_user   int(11) comment '入库人（入库确认的人）',
  putin_date  char(8) comment '入库日期',
  putin_storehouse_code  varchar(50) comment '入库仓库编码',
  cancel_user int(11) comment '退库人（退库入库为物资退库的人，移库为移出库人）',
  contract_no	varchar(50) comment '合同号（采购入库时）',
  putout_storehouse_code  varchar(50) comment '出库仓库编码（移库为出库编码）',
  status               int(2) not null comment '1、待确认。2、合格在库（确认）',
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单主表';


alter table jy_storehouse_in add index jsi_putin_scode_index (putout_storehouse_code);


/*==============================================================*/
/* table: jy_storehouse_in_detail         2017/09/24		*/
/*==============================================================*/
drop table if exists jy_storehouse_in_detail;
CREATE TABLE jy_storehouse_in_detail (
  jy_storehouse_in_id int(11) NOT NULL  COMMENT '入库单主表系统id',
  jy_material_id int(11) NOT NULL  COMMENT '物资表表系统id',
  putin_number int(11) COMMENT '入库数量',
  PRIMARY KEY (jy_storehouse_in_id,jy_material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单明细表';






/*==============================================================*/
/* table: jy_storehouse_out          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_storehouse_out;
CREATE TABLE jy_storehouse_out (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  putout_code   varchar(50) NOT NULL unique comment '出库单号',
  putout_type   int(2) not null comment '出库类型：1、申领出库，3、移库出库',
  putout_user   int(11) comment '出库人（出库确认的人）',
  putout_date  char(8) comment '出库日期',
  putout_storehouse_code  varchar(50) comment '出库仓库编码',
  apply_user  int(11) comment '申请人（申领出库为申领人，移库为入库方）',
  putin_storehouse_code  varchar(50) comment '入库仓库编码（移库出库时）',
  status          int(2) not null comment '1、待确认。2、确认出库',
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单主表';

alter table jy_storehouse_out add index jso_putout_scode_index (putout_storehouse_code);


/*==============================================================*/
/* table: jy_storehouse_out_detail         2017/09/24		*/
/*==============================================================*/
drop table if exists jy_storehouse_out_detail;
CREATE TABLE jy_storehouse_out_detail (
  jy_storehouse_out_id int(11) NOT NULL  COMMENT '出库单主表系统id',
  jy_material_id int(11) NOT NULL  COMMENT '物资表表系统id',
  putout_number int(11) COMMENT '出库数量',
  PRIMARY KEY (jy_storehouse_out_id,jy_material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单明细表';




/*==============================================================*/
/* table: jy_apply          2017/09/24		*/
/*==============================================================*/
drop table if exists jy_apply;
CREATE TABLE jy_apply (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  apply_code   varchar(50) NOT NULL unique comment '申领单号',
  apply_user  int(11) comment '申请人',
  apply_date  char(8) comment '申请日期',
  review_user  int(11) comment '审核人',
  review_date  char(8) comment '审核日期',
  review_remark   varchar(500)  comment '审核意见',
  storehouse_user int(11) comment '出库人',
  out_date char(8) comment '出库时间',
  apply_storehouse_code varchar(50) comment '申请物资的仓库编码',
  status          int(2) not null comment '1、待审核。2、审批拒绝 3、待领用（审批通过）4、已领用',
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资申领表';



/*==============================================================*/
/* table: jy_apply_detail         2017/09/24		*/
/*==============================================================*/
drop table if exists jy_apply_detail;
CREATE TABLE jy_apply_detail (
  jy_apply_id int(11) NOT NULL  COMMENT '申领表系统id',
  jy_material_id int(11) NOT NULL  COMMENT '物资表系统id',
  apply_number int(11) COMMENT '申领数量',
  PRIMARY KEY (jy_apply_id,jy_material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资申领明细表';



/*==============================================================*/
/* table: jy_material_use        2017/09/24		*/
/*==============================================================*/
drop table if exists jy_material_use;
CREATE TABLE jy_material_use (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',	
  jy_material_id int(11) NOT NULL  COMMENT '物资表系统id',
  use_user  int(11) comment '使用人',
  use_date  char(8) comment '使用日期',
  use_number int(11) COMMENT '使用数量',
  status        int(2) not null comment '1、已使用', 
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资使用登记表';



/*==============================================================*/
/* table: jy_material_warn        2017/09/24		*/
/*==============================================================*/
drop table if exists jy_material_warn;
CREATE TABLE jy_material_warn (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',	
  jy_material_id int(11) NOT NULL  COMMENT '物资表系统id',
  warn_storehouse_code varchar(50) comment '预警物资的仓库编码',
  warn_number int(11) COMMENT '预警数量',
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资预警表';



/*==============================================================*/
/* table: jy_transfer        2017/09/24		*/
/*==============================================================*/
drop table if exists jy_transfer;
CREATE TABLE jy_transfer (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',	
  transfer_code   varchar(50) NOT NULL unique comment '调拨单号',
  transfer_date  char(8) comment '调拨日期',
  transfer_type  int(2) comment '调拨类型：1、转库、2、退库、3、调拨',
  putin_user   int(11) comment '入库人',
  putin_storehouse_code  varchar(50) comment '入库仓库编码',
  putout_user   int(11) comment '出库人',
  putout_storehouse_code  varchar(50) comment '出库仓库编码',
  status        int(2) not null comment '1、待调拨 2、调拨完成（确认）', 
  remark        varchar(500)  comment '备注',
  inputuser		  	varchar(30) comment '操作人',
  crt_date			  	char(8) comment '创建日期',
  crt_time			  	char(6) comment '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资仓储调拨表';




/*==============================================================*/
/* table: jy_transfer_detail         2017/09/24		*/
/*==============================================================*/
drop table if exists jy_transfer_detail;
CREATE TABLE jy_transfer_detail (
  jy_transfer_id int(11) NOT NULL  COMMENT '物资仓储调拨表系统id',
  jy_material_id int(11) NOT NULL  COMMENT '物资表系统id',
  transfer_number int(11) COMMENT '调拨数量',
  PRIMARY KEY (jy_transfer_id,jy_material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资仓储调拨明细表';





/*==============================================================*/
/* table: jy_user_online                                         */
/*==============================================================*/
drop table if exists jy_user_online;
create table jy_user_online 
(
   sessionid            varchar(32)  not null  comment '会话id',
   sys_user_id          int(11)   	 not null  comment '用户id',
   loginip              varchar(32)  comment '登录ip',
   logindate            char(8)  comment '登录日期',
   logintime            char(6)  comment '登录时间',
   jsstatus             char(1) default '0' comment '会话状态：0:正常，1：停用',
   constraint pk_sys_user_online primary key (sessionid)
)engine=innodb default charset=utf8;
alter table jy_user_online comment '用户登录会话信息';



create table sys_param
(
   moduleid             varchar(32) not null comment '模块id',
   paraid               varchar(32) not null comment '参数id',
   paravalue            varchar(1024) comment '参数值',
   paraname             varchar(100) comment '参数名称，平台自动添加的参数名称会使用统一约定的前缀，正常业务的参数不添加任何前缀',
   groupparaid          varchar(32) comment '分组id，如果分组id为root则表示该记录为分组信息',
   dict                 varchar(32) comment '字典标识',
   functype             varchar(32) comment '输入类型：‘text’-文本输入，‘int’-整数，‘number’-数字，‘select’-下拉单选，‘mselect''-下拉多选，‘date’-日期，‘time''-时间',
   confoption           varchar(1024) comment '控件配置',
   isdisplay            int comment '是否显示：1-显示  其他-隐藏',
   primary key (paraid, moduleid)
)engine=innodb default charset=utf8;
alter table sys_param comment '系统参数表';
