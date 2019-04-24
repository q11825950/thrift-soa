# thrift-client
消费端测试

# thrift-service
服务端测试

# thrift-api
thrift接口文件模块

# thrift-soa  rpc框架
手写的thrift-rpc框架
结合了springboot使用注解的方式定义消费者和服务者
服务的发现使用配置文件方式（回去会改进）

使用方法
  1.springboot启动类中引用对应配置类，服务端引入@Import({RpcServerConfiguration.class})，消费端引入@Import(RpcConsumerConfiguration.class) 
  也可以两个都引入既当消费者又当生产者
  2.服务端的类 继承thrift定义的接口文件生成的类型，并加上@RpcService(version="1.1.2", weight=3)注解，（此服务端需要交给spring进行管理，也就是加上
  spring的@Service ， @Component等注解） HelloService.thrift（thrift接口文件）HelloService.Iface（接口文件生成的class文件）
  
  @Service
  @RpcService(version="1.1.2", weight=3)
  public class HelloServiceImpl implements HelloService.Iface {


      @Override
      public ByteBuffer greet(ByteBuffer name) throws TException {
          String aa = null;
          try {
              aa = new  String(name.array(),"utf-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
          System.out.println(aa);

          return ByteBuffer.wrap(String.format("Hello %s!", aa).getBytes());

      }
  }
  
  3.消费端引入接口文件生成的接口并加上@RpcReference(serverKey = "test")注解，然后直接使用接口调用即可
  @RpcReference(serverKey = "test")
	SyncRequestService.Iface syncRequestService;


	@RequestMapping("/test")
	@ResponseBody
	public void test(String aa ) throws TException{
		ByteBuffer byteBuffer = syncRequestService.SyncRequest(ByteBuffer.wrap(aa.getBytes()), 1);
		System.out.println(new String(byteBuffer.array()));
	}
  
  4.配置文件说明
  #注册中心配置（因为还没有实现，所以暂时无用，注册中心用起来后下面消费端和服务端的配置也会省略掉）
  rpc:
      registry:
          #注册中心类型property,redis
          protocol: property
          #注册中心地址集合
          list: 192.168.1.138:9899
          
  #rpc调用方式
  thrift:
      server:
          #rpc调用方式（通用配置）
          protocol: thrift
          
          #消费端配置
          #服务端信息集合（classcourse，loginaccept等是区分服务的名称，后面是服务对应的ip和端口多个地址用|隔开）
          list: classcourse=192.168.1.102:9891|192.168.1.103:9891,loginaccept=192.168.1.138:9899,taskserver=192.168.1.138:9897,classSpaceServer=192.168.1.138:9898
          
          #服务端
          #端口
          port: 9891
          #服务端ip地址（服务会自己获取ip地址区启动服务，此处只是为了查看方便）
          host: 192.168.1.138
         
