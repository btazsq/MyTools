# 安卓工具类 #   
## 网络请求工具类 ##  
```
app\src\main\java\com\example\testtools\network\requestion内   
```
#### 目前有4个类，2个接口 ####
尝试采用"Builder模式"和"单例模式"
一开始没有单例，使用RequestionBuilder对象的build()方法可以返回BTArequest的单例   
之后就可以使用BTArequest的静态方法getOnlyEmbody()得到单例了（   
   
2个接口可以不用在意，使用的时候用匿名类或者lamda表达式就行了   
实在要用，DefineAction是指提交的任务，OnRequestion是任务结束之后执行的   
   
目前构造器只有设置编码格式和连接等待时间两个选项，以后要用到什么可以加   
使用例子  
```
stringReturn = new StringReturn(()->{
            Toast.makeText(MainActivity.this,stringReturn.getBack(),Toast.LENGTH_LONG).show();
            Log.d("******", "onCreate: " + stringReturn.getBack());
        });
new RequestionBuilder()
                .build()
                .setcType(BTArequest.TYPE_FORM)
                .fromWeb("http://bihu.jay86.com/login.php")
                .setWhileTime(50L)
                .setRequestMethod("POST")
                .postData(new StringReturn()
                        .setData("username=zsq5&password=zsqzsq"))
                .getStringReturn(stringReturn)
                .disconnect();
```   
fromWeb方法可以打开一个链接，返回一个BTAConnection对象   
setWhileTime是get数据循环等待时间（每隔这个时间就检测一次post有没有结束）   
StringReturn类封装了网络请求的数据   
可以在new的时候传入请求结束时执行的方法   
setData则是post的数据   
postData是提交数据；getStringReturn是接受数据   

另外，BTArequest类有个submitTask方法，可以把需要另开线程执行的任务和回调的任务传进去执行  
   
##### 更新BTAJson类 #####  
使用两个静态方法，分别解析出BTAJson对象和List<BTAJson>对象   
BTAJson对象公开的只有getString(String key):String和toString():String两个方法   
