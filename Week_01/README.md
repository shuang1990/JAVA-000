##### 第一周必做作业题

######1、自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内 容是一个 Hello.class 文件所有字节(x=255-x)处理后的文件。文件群里提供。
* 见Assignment包下的AssignmentHelloClassLoader类
######2、画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的 关系。
* 各参数含义
    * Xms: 指定堆内存空间的初始大小
    * Xmx: 指定最大堆内存
    * Xmn: 新生代堆内存大小
    * Meta: metaspace内存大小
    * DirectMemory: 系统可以使用的最大堆外内存
    * Xss: 每个线程栈的字节数