 依赖:
 
 debugCompile project(path: ':Lib-MVP', configuration: 'debug')
 releaseCompile project(path: ':Lib-MVP', configuration: 'release')

相关问题：

问题1.基类中可以加入findViewById的公共方法来简化Activity的代码量

 解决方案：
 1、XUtils 其本质通过反射性能损耗较大（详细可以深入研究，实现比较简单）
 2、ButterKnife 相比XUtils无性能损耗（详细可以深入研究-注解处理器）
 3、Lib_MVP库中提供的BaseBindActivity/BaseBindFragment配合BaseBindView降低BindView代码在Activity耦合
 
 在需要大量findView时，建议可用使用BaseBindActivity/BaseBindFragment来降低Activity/Fragment臃肿。

问题2.现在保宝在AndroidManifest中基本每个Activity都设置了android:screenOrientation="portrait”，是否应该在基类中默认设置竖屏并不可转动，特殊需求暴露方法来另行设置

 解决方案：（细说该问题其实涉及的内容有点深、细；与PMS、AMS等相关）
 仍然要通过manifest.xml中进行配置（代码配置不等同于该配置，虽然Base类中已经提供）
 
 
3.动态权限的申请是否应该放在基类中来简化调用代码量

 解决方案：
 
 这个要完善ing
 

4使用中会出现Basemodel发起请求中导致的内存泄漏

 解决方案：
 最新的Lib_MVP彻底避免MVP框架可能带来的泄漏问题
 
 对Presener、BindView也提供生命周期，便于检查泄漏发生
 
 
5.使用中设置hasTitleBar为false且自己设置的标题栏为沉浸式的时候 加载出错的背景覆盖高度出错，可能是我的自定义标题设置问题，需要后续确定一下

 解决方案：
 
 Lib-MVP下提供了BaseActivity和BaseFragment完成统一样式的管理，

 其余需求通过LoadStyle2自行指定
 
    
    
    
 
 