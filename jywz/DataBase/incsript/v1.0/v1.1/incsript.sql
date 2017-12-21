/*20171221 新增物资使用类型、营销系统工作单号 */
ALTER TABLE jy_material_use ADD use_type  int(2) comment '使用类型：1：安装使用，2、培训使用';
ALTER TABLE jy_material_use ADD use_workno varchar(100) comment '营销系统工作单号,当安装使用时必需存在值';



delete from jy_params;
insert into jy_params(param_type,param_code,param_name) values('organize','organize01','计量中心');
insert into jy_params(param_type,param_code,param_name) values('organize','organize02','电网计量班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize03','质检班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize04','计量自动化班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize05','用户计量一班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize06','用户计量二班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize07','用户计量三班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize08','室内检定班');
insert into jy_params(param_type,param_code,param_name) values('organize','organize09','电能量数据班');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse01','渔湖周转仓');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse02','生产材料室');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse03','揭东供电局计量运维班材料室');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse04','揭西供电局计量运维班材料室');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse05','普宁供电局计量运维班材料室');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse06','惠来供电局计量运维班材料室');
insert into jy_params(param_type,param_code,param_name) values('storehouse','storehouse07','榕城供电局计量运维班材料室');
