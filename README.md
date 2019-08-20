# FastURL
以FastURL为主的快速解析工具
基于scala2.10版本，打包后java也可以使用
目前进度：2.0

FastURL ： URL的解析工具，实例内采用链式方法，获得实例后多次执行方法后用.toMap或.toJsonString收尾。
使用案例：String json = FastURL(urlString).putIPtoMap("1.2.3.4", false).putAgentToMap("b_iev").remove("b_iev").toJsonString
说明：用一条带有url格式的string创建一个FastURL，添加ip的解析结果（url中不存在ip，所以自己输入ip的值，然后选择false），添加浏览器信息的解析结果（url中存在，key为"b_iev"），删除原先的"b_iev"，将最终结果转成json格式的字符串输出。

AgentUtil：agent解析工具，较上版本添加了实例类，实现自定义输出

IpUtil：   ip解析工具，较上版本分离了数据库，并将ip解析单独设为实例类，实现了自定义输出