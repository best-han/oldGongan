package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.utils.FaceContrastUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.mpi.dao.*;
import com.windaka.suizhi.mpi.model.Person;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PersonInfoIsCrimeTask {
    @Autowired
    PersonDao personDao;

    @Resource
    MsgSocketIdDao msgSocketIdDao;

    int lastId = 0;

    public void executeInternal() throws Exception {
    	log.info("新的人员信息录入去犯罪人员库做比对定时任务开启。。。。。");
        lastId = msgSocketIdDao.queryMsgSocketMaxId("person_info");

        List<Person> personList = personDao.queryPersonNoCompareList(lastId);
        personList.forEach(temp -> {
            // 头像图片
           /* File file=new File(PropertiesUtil.getLocalTomcatImageIp() + temp.getImgPath());
            if(file.exists()){//若图片存在，则推送  hjt*/
                personImageToFaceCrime(temp);
           /* }else{//gongan
                try{
                    if(QueueUtils.getQueuePersonInfoIsCrime()!=null || QueueUtils.getQueuePersonInfoIsCrime().size()==QueueUtils.FILE_QUEUE_SIZE)
                        QueueUtils.getQueuePersonInfoIsCrime().take();
                    QueueUtils.put(temp);//放入队列等待定时任务推送
                }catch (Exception e){
                    e.printStackTrace();
                }

            }*/
            lastId = temp.getId();
        });
        Map<String, Object> params = new HashMap<>();
        if (personList.size() > 0) {
            params.put("recordName", "person_info");
            params.put("maxId", lastId);
            msgSocketIdDao.updateMsgSocketMaxId(params);
        }
    }

    /**
     * 将表中记录去和人脸服务器犯罪人员比对 hjt
     * @param temp
     */
    public boolean personImageToFaceCrime(Person temp){
        boolean result=false;
        // 头像图片
        File file=new File(PropertiesUtil.getLocalTomcatImageIp() + temp.getImgPath());
        if(file.exists()) {//若图片存在，则推送  hjt
            String strBase64 = null;
            try {
                InputStream in = new FileInputStream(file);
                // in.available()返回文件的字节长度
                byte[] bytes = new byte[in.available()];
                // 将文件中的内容读入到数组中
                in.read(bytes);
                strBase64 = new BASE64Encoder().encode(bytes);//将字节流数组转换为字符串
                in.close();
                //增加人脸特征给人脸服务器以便其提取功能
                int id=temp.getId();
                Map mapFaceContrast = FaceContrastUtil.addFace(id, strBase64, "2");
                if (StringUtils.isBlank((String)mapFaceContrast.get("id"))) {
                    log.info("PersonInfoIsCrimeTask人脸特征存入失败");
                }else{// if("noId".equals(mapFaceContrast.get("result")))
                    Map<String,Object> params=new HashMap<>();
                    params.put("faceTypePersonId",mapFaceContrast.get("id"));
                    params.put("id",id);
                    personDao.updatePsersonFaceTypePersonId(params);
                }
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            result=true;//只要图片存在就是成功比对过了，将其从队列中移除
        }
        return result;
    }

    public static void main(String[] args){
        // 头像图片
        File file=new File("C:\\Users\\Lenovo\\Desktop\\1.jpg");
        if(file.exists()) {//若图片存在，则推送  hjt
            String strBase64 = null;
            try {
                InputStream in = new FileInputStream(file);
                // in.available()返回文件的字节长度
                byte[] bytes = new byte[in.available()];
                // 将文件中的内容读入到数组中
                in.read(bytes);
                strBase64 = new BASE64Encoder().encode(bytes);//将字节流数组转换为字符串
                in.close();
                //增加人脸特征给人脸服务器以便其提取功能
                //  int id=temp.getId();
                Map mapFaceContrast = FaceContrastUtil.addFace(1, strBase64, "2");
                if (StringUtils.isBlank((String) mapFaceContrast.get("id"))) {
                    log.info("PersonInfoIsCrimeTask人脸特征存入失败");
                } else {// if("noId".equals(mapFaceContrast.get("result")))
                    Map<String, Object> params = new HashMap<>();
                    params.put("faceTypePersonId", mapFaceContrast.get("id"));
                    params.put("id", 1);
                    //  personDao.updatePsersonFaceTypePersonId(params);
                }
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }



}
