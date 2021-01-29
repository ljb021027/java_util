package com.MyUtils.learn.sort.code;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ljb
 * @since 2018/12/23
 */
public class MergeFile {

    public static final String fileRoot = "E:\\IDE\\idea_workspace\\MyUtils\\src\\test";

    //5MB
    public static final int size = 1 * 1024 * 1024;

    public static void main(String[] args) {
        List<ReadFile> readFilesList = new ArrayList<>();
        BufferedWriter fw = null;
        try {
            List<Integer> lines = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                ReadFile readFile = new ReadFile(fileRoot + "\\testMerge" + i + ".txt");
                readFilesList.add(i, readFile);
                lines.add(Integer.parseInt(readFile.getLine()));
            }
            String outhPath = fileRoot + "\\merge.txt";
            FileWriter fs = null;
            try {
                fs = new FileWriter(outhPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fw = new BufferedWriter(fs, size);
            while (!readFilesList.isEmpty()) {
                //find min in lines
                int index = 0;
                int comp = lines.get(index);
                for (int i = 1; i < lines.size(); i++) {
                    Integer next = lines.get(i);
                    if (comp > next) {
                        comp = next;
                        index = i;
                    }
                }
                fw.write(comp + "\n");
                String line = readFilesList.get(index).getLine();
                if (line == null) {
                    readFilesList.remove(index);
                    lines.remove(index);
                    continue;
                }
                int nextpo = Integer.parseInt(line);
                lines.set(index, nextpo);
            }
            fw.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            readFilesList.forEach(ReadFile::destory);
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static void crateFile() {
        String path = "E:\\IDE\\idea_workspace\\MyUtils\\src\\test";
        for (int i = 0; i < 6; i++) {
            File file = new File(path + "\\testMerge" + i + ".txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                int max = 10000000;
                List<Integer> lines = new LinkedList<>();
                for (int j = 0; j < max; j++) {
                    int v = (int) (Math.random() * max);
                    lines.add(v);
                }
                Collections.sort(lines);
                lines.forEach(s -> {
                    try {
                        bw.write(String.valueOf(s));
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    static class TestCreate {
        public static void main(String[] args) throws Exception {
            crateFile();
        }
    }


    static class ReadFile {
        private String filePath;

        private BufferedReader br;

        public ReadFile(String filePath) {
            File file = new File(filePath);
            try {
                br = new BufferedReader(new FileReader(file), size);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private String getLine() {

            try {
                String line = br.readLine();
                if (StringUtils.isNotEmpty(line)) {
                    return line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void destory() {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
