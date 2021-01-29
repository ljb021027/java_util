import java.io.*;

/**
 * Created by ljb on 2017/9/14.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        String srcPath = "C:\\Users\\ljb\\Documents\\Tencent Files\\415888451\\FileRecv\\assets\\Assets";
        String outPath = "C:\\Users\\ljb\\Documents\\Tencent Files\\415888451\\FileRecv\\assets\\out\\";
        File file = new File(srcPath);
        File[] files = file.listFiles();
        for (File f : files){

            InputStream is = new FileInputStream(new File(f.getAbsolutePath()));
            OutputStream os = new FileOutputStream(new File(outPath+f.getName()+".jpg"));
            byte[] buf = new byte[1024];
            int n =0;
            while((n = is.read(buf))!=-1){
                os.write(buf, 0, n);
            }

        }


    }
}

