api网关

发布记录：

2019.2.13 - 1.1.0
	1. 升级spring boot到2.1.2
	2. 升级spring-cloud到Greenwich.RELEASE
	3. 累计包含中间版本增加的其他Filter和配置文件

2018.9.27 - 1.0.0
	1. 支持通过路径参数设置Header
	2. 支持Jwt解析，issuer参数自动设置到Header中
	3. 此版本通过Jwt验证进行简单的访问控制，下一版计划实现基于角色和标签的访问控制
