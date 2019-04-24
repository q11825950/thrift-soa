package com.github.mql.rpc.thrift;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 对象连接池工厂
 * Created by Administrator on 2019/2/26 0026.
 */
public class KeyPoolFactory {
    private static final Logger logger = LoggerFactory.getLogger(KeyPoolFactory.class);
    /**
     * 对象池
     */
    private static GenericKeyedObjectPool<String, TSocket> pool;
    /**
     * 对象池的参数设置
     */
    private static final GenericKeyedObjectPoolConfig config;

    /**
     * 对象池每个key最大实例化对象数
     */
    private final static int TOTAL_PERKEY = 10;
    /**
     * 对象池每个key最大的闲置对象数
     */
    private final static int IDLE_PERKEY = 3;

    static {
        config = new GenericKeyedObjectPoolConfig ();
        config.setMaxTotalPerKey(TOTAL_PERKEY);
        config.setMaxIdlePerKey(IDLE_PERKEY);
        /** 支持jmx管理扩展 */
        config.setJmxEnabled(true);
        config.setJmxNamePrefix("myPoolProtocol");
        /** 保证获取有效的池对象 */
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
    }

    /**
     * 获取对象池
     * @return
     */
    public static GenericKeyedObjectPool getPool(){
        if (pool == null) {
            init();
        }
        return pool;
    }


    /**
     * 从对象池中获取对象
     * @param key
     * @return
     * @throws Exception
     */
    public static TSocket getTSocket (String key) throws Exception {
        if (pool == null) {
            init();
        }
        return pool.borrowObject(key);
    }

    /**
     * 归还对象
     * @param key
     * @param socket
     */
    public static void returnBean (String key, TSocket socket) {
        if (pool == null) {
            init();
        }
        pool.returnObject(key , socket);
    }

    /**
     * 关闭对象池
     */
    public synchronized static void close () {
        if (pool !=null && !pool.isClosed()) {
            pool.close();
            pool = null;
        }
    }
    /**
     * 初始化对象池
     */
    private synchronized static void init () {
        if (pool != null) return;
        logger.info("初始化客户端对象池"+config);
        pool = new GenericKeyedObjectPool<>(new ThriftSocketPoolFactory(), config);
    }

}
