# FastURL
快速解析URL工具
基于scala2.10版本，打包后java也可以使用

FastURL ： URL的解析工具，实例内采用链式方法，获得实例后多次执行方法后用.toMap或.toJsonString收尾。
-使用案例：String json = FastURL(urlString).putIPtoMap("1.2.3.4", false).putAgentToMap("b_iev").remove("b_iev").toJsonString
-说明：用一条带有url格式的string创建一个FastURL，添加ip的解析结果（url中不存在ip，所以自己输入ip的值，然后选择false），添加浏览器信息的解析结果（url中存在，key为"b_iev"），将最终结果转成json格式的字符串输出。

伴生类集成了ip解析方法和Agent解析方法，可以直接使用。

IpUtil ： Ip解析工具，仅有一个对外API：select(ip)(ip解析方法)

AgentUtil: Agent解析工具，对外有三个API：分别可以获取browers、OS和all的解析结果，返回的是Map。