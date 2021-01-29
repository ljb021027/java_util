import redis.clients.jedis.Jedis;

/**
 * Created by ljb on 2017/11/23.
 */
public class JedisTest {

    public static void main(String[] args) {
//        String host = "localhost";
        String host = "192.168.0.202";
        int port = 6379;

        Jedis jedis = new Jedis(host,port);
        jedis.auth("d524045429941c");
        jedis.select(5);
        int num = 10000 ;
//        int num = 1;
        String[] sts = new String[num];
        for (int i = 0; i < num; i++) {
            sts[i] = "00016000000000000"+i+","+"1";
        }
        Long sadd = jedis.sadd("MP:CLOUD:TRADE:WARNING:NOTITY", sts);
        System.out.println(sadd);

    }
}
