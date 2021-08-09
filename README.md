# Automatic-capture-of-Weibo-traffic
自动捕获微博的数据流量（PC web端）
## 简介
在进行应用流量分析和识别的时候，有时需要收集一定数量的对应流量，以进行流量特征的提取并进行模型的训练和测试。
在这里，介绍了一种可以自动产生并捕获应用web网页端流量的方法（以微博网页端为例），同类型的网络应用也可以通过类似的方法来实现。
## 主要技术
openQPA + Selenium + Java（Python）<br>
### openQPA
如果没有抓取web端纯净流量的需求可以跳过该部分的介绍（或使用tcpdump等工具作为替代）。<br><br>
openQPA是一款开源开放的基于进程抓包并提供简易分析功能的一个捕包工具（ http://www.l7dpi.com/ ）。虽然该工具对外提供了源代码（Python），但是并不支持通过命令行进行调用，只能手动通过其图形化界面进行相应的操作。因此，我们首先需要考虑对其源码进行分析，获取openQPA完成基于进程抓包的功能模块以通过后续的代码进行调用。<br><br>
具体分析过程在此处略过。总的来说，通过代码分析和反编译，我们定位到了其实现功能的主要部分（ /MyCap/src/connect/ ），可以看到其包含了一些可执行exe文件和相关的dll运行时库。其中，NIX.exe的功能为获取本机上的网卡信息（如果本机上有多个网卡，在后续进行捕包的时候我们需要指定主要进行流量交互的那个网卡）。CAP.exe则是openQPA实现基于进程抓包的主要功能模块，通过反编译获取到的大致调用指令为<br>
```
.\CAP.exe U H U nic_number data_path 0
```
其中 `nic_number` 为进行流量捕获的网卡的序号（可通过NIC.exe获得并进行指定）, `data_path` 为捕获到的进程流量pcap数据包的存储位置。另外一些别的参数如 ` U H U ` 等在此没有进行深入的分析，有兴趣的话可以在openQPA的官网上下载源码查看。<br><br>
另，使用openQPA捕包模块需要安装响应的执行环境，具体可参考 https://www.cnblogs.com/pealicx/p/10617575.html 
### selenium
Selenium是用来进行web应用程序测试的强大工具。它并不是一个单一的工具，其中封装了各种各样的自动化与测试的工具和库。<br>
（ https://github.com/SeleniumHQ/selenium ）。因此，我们选用selenium和Google的chromedriver驱动来完成对用户点击、输入、表单提交等操作的模拟。<br><br>
PS. 需要注意一下Chrome浏览器和chromedriver驱动的版本对应情况，chromedriver的下载地址为 http://chromedriver.storage.googleapis.com/index.html 
## 实现方式
大致的实现方式为，首先启动CAP.exe进程（或使用tcpdump作为代替）用于捕获各个进程的流量数据，然后通过selenium启动chromedriver驱动打开浏览器页面，打开对应的应用后（如微博网页端）通过selenium的一些定位函数等，获取页面元素并进行点击、输入、提交等等一系列操作产生对应的流量，执行完之后终止捕包进程最后进行数据的打包保存。<br><br>
项目的各部分内容：<br>
* `cap`     //捕包类的实现
* `capdata` //存储最后打包后的数据
* `connect` //openQPA的基于进程捕包的功能模块
* `cookie`  //保存访问页面的cookie，可以省去后续的登录步骤，具有时效性
* `drivers` //chromedriver驱动
* `lib`     //selenium依赖包
* `test`    //主要运行的类，包括微博控制等
* `tools`   //一些使用到的工具类

运行主类 `src/test/WeiboCtrl` ，如果此时已经有保存好的cookie，则可以自动读取保存的cookie进行登录，并继续执行后续的一系列设定好的操作。如果此时没有保存好的cookie或者cookie已经失效，就会首先进行一次登录。<br><br>
由于目前微博网页端进行登录的时候无法直接通过用户名和密码，因此需要手动进行短信验证或者通过手机端扫码登录。此时程序会进入等待状态，当手动扫码或者短信验证完成之后，在控制台输入任意字符，则可以使程序继续运行。这时程序会将登录时的cookie记录下来，在处理之后保存在cookie文件夹下。这样在下一次程序执行的时候，如果该cookie还未失效，则程序就可以直接读取该cookie进行登录，从而省去较为麻烦的登录过程。<br><br>
在网页端进行的一系列操作的代码，位于 `test/Myprocess1` 中，在不同的网页端进行操作时可以根据自己的需要自行编写对应的selenium脚本代码以产生需要的流量。



