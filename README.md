# XposedHook
---
### 说明
* 提供了hook方法和构造器的简单通用实现，无需为hook的方法书写单独的测试代码
* 需要hook的内容通过YAML格式的文件进行配置，修改配置后，会自动生效
* 因为XposedHook会在每一个dalvik中加载运行，配置文件会被重复解析并输出大量verbose level日志
* XposedHook未经过充分测试 :)

