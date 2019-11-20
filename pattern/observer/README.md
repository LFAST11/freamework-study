## 观察者模式

定义对象之间的一对多依赖，让多个观察者对象同时监听一个主题对象，当主题对象发生变化的时候，所有依赖者（观察者）都会收到通知并更新

观察者模式属于行为型模式，也称作发布订阅模式


## 观察者模式的优点
1. 观察者和被观察者之间建立了一个抽象的耦合
2. 观察者模式支持广播通信

## 观察者模式的缺点
1. 观察者之间有过多的细节依赖，提高时间消耗和程序复杂度
2. 使用要得当，避免循环调用
