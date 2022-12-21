package com.ebook.backend.utils.Imageutils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class ImageInsert {

    //本地图片转为Base64
    public static String GetImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try (FileInputStream fileInputStream = new FileInputStream(imgFile)) {
            // 将文件内容读取到字节数组中
            byte[] imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);

            // 将字节数组编码为Base64字符串
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(imageBase64);
            return imageBase64;//返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        /*try
        {
            in = Files.newInputStream(Paths.get(imgFile));
//            data = new byte[in.available()];
//            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();*/
//        return imageBase64;//返回Base64编码过的字节数组字符串
    }

    //Base64格式图片转储到本地
    public static boolean GenerateImage(String base64str,String savepath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (base64str == null) //图像数据为空
            return false;
        // System.out.println("开始解码");
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            //  System.out.println("解码完成");
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            // System.out.println("开始生成图片");
            //生成jpeg图片
            OutputStream out = new FileOutputStream(savepath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}