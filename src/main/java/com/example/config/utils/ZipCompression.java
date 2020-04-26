package com.example.config.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompression
{
    /**
     * 压缩文件
     * @param zipFileName 压缩文件保存名
     * @param target 目标文件路径
     */
    public static void filetoZip(String zipFileName,File target)throws RuntimeException{
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
            do_file_toZip(target,zos,target.getName());//开始压缩
        } catch (Exception e) {
            throw new RuntimeException("压缩失败",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile   源文件
     * @param zos         zip输出流
     * @param name        压缩后的名称
     */
    private static void do_file_toZip(File sourceFile, ZipOutputStream zos, String name) throws Exception {
        byte[] buf = new byte[1024 * 2];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                zos.putNextEntry(new ZipEntry(name + "/"));// 空文件夹的处理
                zos.closeEntry();// 没有文件，不需要文件的copy
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    do_file_toZip(file, zos, name + "/" + file.getName());
                }
            }
        }
    }
}
