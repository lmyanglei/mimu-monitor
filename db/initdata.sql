INSERT INTO `monitor_config_data`
    (`id`, `create_time`, `update_time`, `delete_status`, `service_name`, `service_url`, `service_check_type`, `service_environment`, `notify_emails`, `notify_mobiles`, `webhook_url`, `webhook_keyword`, `webhook_group_name`, `cron`, `timeout`, `network`, `enable_status`)
    VALUES (1, '2022-06-26 11:43:43', '2022-06-26 11:43:47', 0, '百度（baidu）', 'http://www.baidu.com', '1', 'dev', 'lmyanglei@gmail.com', '1380138000', 'https://oapi.dingtalk.com/robot/send?access_token=420b1a020042e957f94705830dda5ce247efa4f82686f3435b576e22d956f800', '服务监控', '自己测试', '0 0/10 * * * ? ', 10000, 'inner', 0);
