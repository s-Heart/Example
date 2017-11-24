题目：
在/tol/htdocs目录下有269个合计20G的应用访问日志文件，请计算：
1.       study.koolearn.com的访问总数；
2.       平均响应时间最快的top10域名、次数、平均响应时间(单位s，保留小数点后3位)，按响应时间升序排列。
3.       计算结果可以在控制台打印，也可以输出到/tmp目录的文件中，要规定格式输出结果
----------------------开始1xxxxxxxxxx(当前时间戳)-----------------------
----------------------结果------------------------
study.koolearn.com       1111111
xxx1.koolearn.com         1111111         0.004s
xxx2.koolearn.com         1111111         0.005s
 
----------------------结束1xxxxxxxxxx(当前时间戳)-----------------------
 
----------------------总耗时：xxxx ms---------
 
 
附件为其中一段日志文本，供编程调试用
评比规则：
1.     结果不准确直接pass掉；
2.     违规使用第三方工具pass；
3.     运算时间最短 前三名。
注意事项：
1.       正常的日志格式（以附件样例为准）：$http_x_forwarded_for [$time_local] $request $status $http_referer $body_bytes_sent $content_length $request_time $htt 
p_user_agent  $sso_id_cookie  $server_name $upstream_status $upstream_addr $upstream_response_time $user_cookie  用制表符分割；
2.       日志中有噪音数据；
3.       每个日志文件300000行，但大小有区别。
