# 获取原始测试用例的执行逻辑
1. mcr-scheduler.reex.Junit4MCRRunner.getNewExploreationThread.run()调用
2. mcr-scheduler.reex.Junit4MCRRunner.runchild()调用
3. mcr-scheduler.reex.Junit4MCRRunner.exploreText()调用
4. mcr-sheduler.reex.Junit4MCRRunner调用
5. mcr-sheduler.reex.sheduler.completedscheduleExecution()调用
6. mcr-sheduler.strategy.MCRStrategy.completedSchedulerExecution()调用
7. mcr-sheduler.strategy.MCRStrategy.executeSingleThread()调用
8. mcr-constraints.StartExploring.execute()调用
9. mcr-constraints.ExploreSeedInterleavings.Execute()调用
10. mcr-constraints.ExploreSeedInterleavings.generateCausallyDifferentsSchedules()：负责获取原始测试的执行轨迹

从上面的调用关系可知，第10个函数负责获取原始测试用例的执行逻辑。为了让测试自动化进行，注释掉了generateCausallyDifferentsSchedules()方法的第199到335行的代码，中断了z3的执行。


# 获取原始测试用例的轨迹之后，提取读写事件
ExploreSeedInterleavings.generateCausallyDifferentsSchedules()中的代码（146行）


# 根据原始测试用例的读写事件匹配蜕变模式组
cmtester/src/main/java/algorithm/obtainpattern/Test.java编辑了识别可运用的蜕变模式的逻辑

# 根据匹配到的蜕变模式生成衍生测试用例的执行轨迹
逻辑封装在每一个待测对象的蜕变模式组中

# 控制衍生测试用例按照既定的轨迹进行执行
首先在Instrumenter中添加一个else if语句段，在语句段中主要修改ClassVisitor实例化的对象
然后为测试对象创建一个ClassTransformer4SUT类，复制其他这种类的内容，并修改mv的实例化对象
接着创建一个MethodTransformer4SUT类，复制其他这种类的内容，并修改mv.visitMethodInsn和mv.visitMethodInsn中的Instrumentor.EVENT_RECIEVER_X的值
再者在scheduler包下创建Scheduler4SUT，复制其他这种类的内容，并修改choose(主要修改Strategy实例化的对象)和守护线程的逻辑
最后在strategy包下创建Strategy4SUT供choose方法调用（基本都要重写）





cmtester/src/main/java/test下对应的待测对象名字下的可执行脚本，例如CriticalTest（控制整个主席那个过程），内部调用：
1. cmtester/src/main/java/instrument字节码插装
2. 执行新的字节码文件时，会调用scheduler类（每一个待测程序又自己的类）：首先执行到调度点（读写事件）暂停，然后守护进程决定哪一个线程执行，具体的决定逻辑在strategy类中（每一个待测程序实现一个新逻辑）

# 扩展实验遇到的问题
- 当共享变量是数组时(BubbleSort、bufwriter)，JMCR不支持
```json
1 0 BubbleSortTest.java:15 null WRITE BubbleSortTest.java.testSortPositiveNumbers

2 0 BubbleSortTest.java:15 edu.tamu.aser.tests.bubblesort.BubbleSort.arr WRITE BubbleSortTest.java.testSortPositiveNumbers

12 1 OneBubble.java:40 null READ OneBubble.java.run

14 1 OneBubble.java:40 edu.tamu.aser.tests.bubblesort.BubbleSort.arr READ OneBubble.java.run

16 1 OneBubble.java:50 null READ OneBubble.java.SwapConsecutives

19 1 OneBubble.java:51 edu.tamu.aser.tests.bubblesort.BubbleSort.arr READ OneBubble.java.SwapConsecutives

20 1 OneBubble.java:51 null WRITE OneBubble.java.SwapConsecutives

22 1 OneBubble.java:52 edu.tamu.aser.tests.bubblesort.BubbleSort.arr WRITE OneBubble.java.SwapConsecutives
```



# PingPong程序适合的场景
1，2，3，5


