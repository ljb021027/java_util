import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class Test {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\ljb\\Desktop\\中金银行.txt";
        RandomAccessFile rf = new RandomAccessFile(path, "rw");
        String line = null;
        int id = 11020350;

        while ((line = rf.readLine()) != null) {
            line = new String(line.getBytes("ISO-8859-1"), "utf-8");
            String[] split = line.split(" ");
            String[] split2 = split[1].split("\\（");
//			System.out.println("INSERT INTO sys_trs_bankcode VALUES ("+ id++ +", 150, '"+ "" +"', '"+ split[0] +"', 0, '"+ split2[0] +"', NULL);");
//			System.out.println("INSERT INTO sys_trs_channel_route VALUES ("+ "NULL" +", 48, '"+ "151" +"', '"+ split[0] +","+split[0]+"', NULL);");
//			System.out.println("INSERT INTO sys_trs_channel_route VALUES (NULL, 48, 151, '"+split[0] +"', '"+split[0]+"', '"+split[1] +"', 0, 1, NOW(), NOW(), NULL);");
            System.out.println("INSERT INTO sys_trs_channel_route VALUES (NULL, 46, 149, '" + "" + "', '" + split[0] + "', '" + split2[0] + "', 0, 1, NOW(), NOW(), NULL);");

        }

    }
}

class Test11 {
    public static void main(String[] args) {
        String path = "E:\\webTool\\apache-maven-3.5.0\\repository\\com\\alibaba\\fastjson\\1.1.35\\fastjson-1.1.35.jar";
        InputStream resourceAsStream = Test11.class.getResourceAsStream(path);
        File file = new File(path);
        System.out.println(file);
    }
}


