CREATE TABLE `monitor_config_data`  (
 `id` bigint(20) NOT NULL  PRIMARY KEY,
 `create_time` datetime NULL DEFAULT NULL,
 `update_time` datetime NULL DEFAULT NULL,
 `delete_status` int(11) NULL DEFAULT NULL,
 `service_name` varchar(100),
 `service_url` text,
 `service_check_type` varchar(255),
 `service_environment` varchar(255),
 `notify_emails` text,
 `notify_mobiles` text,
 `webhook_url` varchar(255),
 `webhook_keyword` varchar(255),
 `webhook_group_name` varchar(255),
 `cron` varchar(255) ,
 `timeout` int(11) NULL DEFAULT NULL,
 `network` varchar(255) ,
 `enable_status` int(11) NULL DEFAULT 0
) ;