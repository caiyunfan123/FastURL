# FastURL
快速解析URL工具
基于scala 2.12.7版本开发，兼容2.10版本，可以自行下载以2.10版本为基础打包使用。

FastURL ： URL的解析工具，实例内采用链式方法，获得实例后多次执行方法后用.toMap或.toJsonString收尾。
          伴生类中则集成了ip解析方法和Agent解析方法，可以直接使用。

IpUtil ： Ip解析工具，仅有一个对外API：select(ip)(ip解析方法)

AgentUtil: Agent解析工具，对外有三个API：分别可以获取browers、OS和all的解析结果，返回的是Map。