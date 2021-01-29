package com.MyUtils.xml;

import java.io.*;

/**
 * @author ljb
 * @since 2019/5/18
 */
public class IargeFileIO {

    public static void main(String[] args) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:\\Users\\ljb\\Desktop\\mipush_user_reginfo.xml")));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存
            FileWriter fw = new FileWriter("C:\\Users\\ljb\\Desktop\\push.txt");
            String out = "";
            while (in.ready()) {
                String line = in.readLine();
                if (line.contains("alias")){
                    continue;
                }
                if (line.contains("field")) {
                    if (line.contains("name=\"id\"")) {
                        out = "";
                    }
                    int s = line.indexOf("\">");
                    int e = line.indexOf("</");
                    String substring = line.substring(s + 2, e);
//                    System.out.println(substring);
                    out = out + " " + substring;

                }
                if (line.contains("</row>")) {
                    fw.append(out);
                    fw.append(System.getProperty("line.separator"));
                    fw.flush();
                }
            }
            in.close();

            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
