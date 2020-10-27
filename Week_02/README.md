# GC分类及原理

标签（空格分隔）： JVM

---

#### 垃圾回收器的种类

* 新生代
    * Serial 单线程
    * ParNew  多线程
    * G1 多线程
* 老年代
    * Serial Old 单线程
    * CMS 多线程
    * G1 多线程


#### CMS垃圾回收原理

* 4个阶段
    * 初始标记
        * 仅标记GC Roots直接引用的对象，速度很快，Stop the World
    * 并发标记
        * 对老年代的对象进行GC Roots追踪，比较耗时
    * 重新标记
        * 重新标记第二阶段里新创建的对象，以及已有对象变成垃圾对象的情况，速度很快，Stop the World
    * 并发清理
        * 清理垃圾对象，比较耗时

* CMS问题
    * 并发标记和并发清理两个阶段虽然不会Stop the World，但是会较长时间占用一部分CPU，会造成CPU资源紧张
    * 当CMS在进行垃圾清理时，又有新的对象进入老年代，此时若老年代预留的内存空间(-XX:CMSInitiatingOccupancyFaction,JDK 1.6默认为92%,当老年代的内存占用超过此参数,会自动触发CMS垃圾回收)不够用，此时会发生Concurrent Mode Failure，就是垃圾回收失败，然后自动用Serial Old垃圾回收器替代CMS，强行Stop the World，重新进行Full GC
    * 内存碎片问题，标识-清理算法会导致很多的内存碎片而使Full GC更加频繁，CMS有一个参数-XX:+UseCMSCompactAtFullCollection，表示多少次Full GC之后再进行一次内存碎片整理，默认为0，意思是每次Full GC之后会进行一次内存整理(Stop the World)
    

#### G1垃圾回改原理

* 原理
    * G1主要是把内存拆分为很多个小的Region，然后新生代和老年代各自对应一些Region，回收的时候尽可能挑选停顿时间最短以及回收对象最多的Region，尽量保证达到我们指定的垃圾回收系统停顿时间
* 主要参数
    * -XX:+UseG1GC  指定使用G1垃圾回收器
    * -XX:G1HeapRegionSize  指定每个Region的大小(JVM最多可以有2048个Region,默认为堆大小/2048)
    * -XX:G1NewSizePercent  设置新生代初始占比(默认新生代对堆内存的占比是5%,维持这个默认值即可)
    * -XX:G1MaxNewSizePercent 设置新生代最大占比(默认为堆内存的60%)
    * -XX:MaxGCPauseMills  设定目标GC停顿时间 (默认200ms)
* 适用场景
    * 大堆内存的系统
    * 延迟敏感的系统(如金融)



#### JVM模板(ParNew + CMS)

* 模板
```
-Xms4096M -Xmx4096M -Xmn3072M -Xss1M  -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFaction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelInitialMarkEnabled -XX:+CMSScavengeBeforeRemark -XX:+DisableExplicitGC -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=/usr/local/app/oom
```