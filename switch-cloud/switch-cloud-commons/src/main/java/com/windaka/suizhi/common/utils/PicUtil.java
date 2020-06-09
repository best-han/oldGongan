package com.windaka.suizhi.common.utils;


import com.windaka.suizhi.common.constants.CommonConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @ClassName PicUtil
 * @Description 图片工具类
 * @Author lixianhua
 * @Date 2019/12/9 15:05
 * @Version 1.0
 */
public class PicUtil {

    /**
     * 功能描述: 获取图片文件名称
     *
     * @auther: lixianhua
     * @date: 2019/12/8 15:21
     * @param:
     * @return:
     */
    public static String getPicName(String tableName, Integer id) {
        String timeStr = TimesUtil.getServerDateTime(8, new Date());
        return tableName + "__base64Img__id__" + String.valueOf(id) + "__" + timeStr + ".jpg";
    }

    /**
     * 将图片压缩包名称分解为指定相对路径，用于内网下载图片后存放指定路径(注意打包时命名中的时间格式是："yyyy-MM-dd-HH-mm-ss")
     * @param zipFileName
     * @return
     */
    public static String picZipNameToRelativePath(String zipFileName) {
        zipFileName=zipFileName.replace(CommonConstants.ZIP_IMAGE_FILE_NAME_PREFIX,"");
        StringTokenizer token = new StringTokenizer(zipFileName, "-");
        String year = token.nextToken();
        String month = token.nextToken();
        String day = token.nextToken();
        return year+ File.separator + month +File.separator +day +File.separator;
    }

    /**
     * 功能描述: base64字符串转字节数组
     *
     * @auther: lixianhua
     * @date: 2019/12/8 15:52
     * @param:
     * @return:
     */
    public static byte[] stringToInputStream(String imgStr) {
        Decoder decoder = Base64.getDecoder();
        byte[] b =  Base64.getMimeDecoder().decode(imgStr);
//        byte[] b = decoder.decode(imgStr);
        return b;
    }

    /**
     * 获取用于保存访问图片的本地相对路径： 年/月/日/   hjt
     * @return
     */
    public static String getPicRelativePath(Date date){
        //Date date=new Date();
        String year=TimesUtil.getYearOfDate(date)+"";
        String month=TimesUtil.getMonthOfDate(date)+"";
        String day=TimesUtil.getDayOfDate(date)+"";
        return year+ File.separator + month +File.separator +day +File.separator;
    }

   /* public static String pngToJpg() throws Exception {

        File file=new File("C:\\Users\\Lenovo\\Desktop\\1.jpg");
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
       // System.out.println( Base64.getEncoder().encodeToString(buffer));
        String base64Image=Base64.getEncoder().encodeToString(buffer);
        //将png图片转换为jpg图片
        try {
            //如果是png格式的则转换为jpg格式，然后再传给人连服务器
            //read image file
            BufferedImage bufferedImage = ImageIO.read(inputFile);
            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
          //  String newPath = imagePath.replace(".png",".jpg");// df.format(new Date())+"_"+userId;
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File(newPath));
            returnPath=newPath;
        }
        catch (Exception ex) {

        }





    }*/



}
