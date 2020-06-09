package com.windaka.suizhi.manageport.wenyuan.controller;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.wenyuan.msg.ReturnMsg;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @project: switch-cloud-manageport
 * @Description: 给山东闻远公司提供文件上传接口
 * @author: kakaxi
 * @date: 2019年12月13日 上午10:16:19
 */
@Slf4j
@Controller
@RequestMapping("/windaka")
public class UploadZipController extends BaseController {
	@Resource
    FtpProperties ftpProperties;
//	public final String BASE_URL = "D:\\wenyuan\\";
	public final String BASE_URL = CommonConstants.WENYUAN_ZIP_PATH;
	public final String FTP_URL = "wenyuan_";
	public final String MAC_URL = "mac";
	public final String IMSI_URL = "imsi";
	public final String CAR_URL = "car";
	public final String FACE_URL = "face";
	public final String SUFFIX = ".zip";

	@ResponseBody
	@PostMapping("/uploadMac")
	public ReturnMsg uploadMac(MultipartFile macZip, HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {

		if (macZip == null) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件为空!", null, "1001");
		}
		if (!macZip.getOriginalFilename().contains(".")) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		String suffix = macZip.getOriginalFilename().substring(macZip.getOriginalFilename().lastIndexOf("."))
				.toLowerCase();

		if (!SUFFIX.equals(suffix)) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		if(macZip.getSize()>2*1024*1024){
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件不能大于2MB!", null, "1003");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		Date tempDate=new Date();
		String date = df.format(tempDate);
		String fileName = FTP_URL+MAC_URL+"_"+date + SUFFIX;
		String dirUrl = BASE_URL + MAC_URL+File.separator+new SimpleDateFormat("yyyyMMdd").format(tempDate);

		File filePath = new File(dirUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File tempFile=new File(dirUrl + File.separator + fileName);
		macZip.transferTo(tempFile);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileInputStream fileInputStream=null;
				try {
					fileInputStream = new FileInputStream(new File(dirUrl + File.separator + fileName));
					Boolean result=FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
		            ftpProperties.getPassword(),ftpProperties.getBasePath(),"",fileName,fileInputStream);
					log.info("["+fileName+"]上传结果："+result);
			        System.err.println("mac...result..."+result);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(fileInputStream!=null){
							fileInputStream.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		return new ReturnMsg(ReturnMsg.SUCCESS, ReturnMsg.SUCCESS_DESC, null, "1000");
	}

	@ResponseBody
	@PostMapping("/uploadImsi")
	public ReturnMsg uploadImsi(MultipartFile ImsiZip, HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {

		if (ImsiZip == null) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件为空!", null, "1001");
		}
		if (!ImsiZip.getOriginalFilename().contains(".")) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		String suffix = ImsiZip.getOriginalFilename().substring(ImsiZip.getOriginalFilename().lastIndexOf("."))
				.toLowerCase();

		if (!SUFFIX.equals(suffix)) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		if(ImsiZip.getSize()>2*1024*1024){
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件不能大于2MB!", null, "1003");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		Date tempDate=new Date();
		String date = df.format(tempDate);
		String fileName = FTP_URL+IMSI_URL+"_"+date + SUFFIX;
		String dirUrl = BASE_URL + IMSI_URL+File.separator+new SimpleDateFormat("yyyyMMdd").format(tempDate);

		File filePath = new File(dirUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File tempFile=new File(dirUrl + File.separator + fileName);
		ImsiZip.transferTo(tempFile);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileInputStream fileInputStream=null;
				try {
					fileInputStream = new FileInputStream(new File(dirUrl + File.separator + fileName));
					Boolean result=FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
		            ftpProperties.getPassword(),ftpProperties.getBasePath(),"",fileName,fileInputStream);
					log.info("["+fileName+"]上传结果："+result);
			        System.err.println("imsi...result..."+result);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(fileInputStream!=null){
						try {
							fileInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		return new ReturnMsg(ReturnMsg.SUCCESS, ReturnMsg.SUCCESS_DESC, null, "1000");
	}

	@ResponseBody
	@PostMapping("/uploadCarNumber")
	public ReturnMsg uploadCarNumber(MultipartFile carNumberZip, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException {

		if (carNumberZip == null) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件为空!", null, "1001");
		}
		if (!carNumberZip.getOriginalFilename().contains(".")) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		String suffix = carNumberZip.getOriginalFilename().substring(carNumberZip.getOriginalFilename().lastIndexOf("."))
				.toLowerCase();

		if (!SUFFIX.equals(suffix)) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		if(carNumberZip.getSize()>2*1024*1024){
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件不能大于2MB!", null, "1003");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		Date tempDate=new Date();
		String date = df.format(tempDate);
		String fileName = FTP_URL+CAR_URL+"_"+date + SUFFIX;
		String dirUrl = BASE_URL + CAR_URL+File.separator+new SimpleDateFormat("yyyyMMdd").format(tempDate);

		File filePath = new File(dirUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File tempFile=new File(dirUrl + File.separator + fileName);
		carNumberZip.transferTo(tempFile);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileInputStream fileInputStream=null;
				try {
					fileInputStream = new FileInputStream(new File(dirUrl + File.separator + fileName));
					Boolean result=FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
		            ftpProperties.getPassword(),ftpProperties.getBasePath(),"",fileName,fileInputStream);
					log.info("["+fileName+"]上传结果："+result);
			        System.err.println("car...result..."+result);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(fileInputStream!=null){
						try {
							fileInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		return new ReturnMsg(ReturnMsg.SUCCESS, ReturnMsg.SUCCESS_DESC, null, "1000");
	}

	@ResponseBody
	@PostMapping("/uploadFace")
	public ReturnMsg uploadFace(MultipartFile faceZip, HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		
		if (faceZip == null) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件为空!", null, "1001");
		}
		if (!faceZip.getOriginalFilename().contains(".")) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		String suffix = faceZip.getOriginalFilename().substring(faceZip.getOriginalFilename().lastIndexOf("."))
				.toLowerCase();

		if (!SUFFIX.equals(suffix)) {
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件格式错误!", null, "1002");
		}
		if(faceZip.getSize()>2*1024*1024){
			return new ReturnMsg(ReturnMsg.FALSE, "上传文件不能大于2MB!", null, "1003");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		Date tempDate=new Date();
		String date = df.format(tempDate);
		String fileName = FTP_URL+FACE_URL+"_"+date + SUFFIX;
		String dirUrl = BASE_URL + FACE_URL+File.separator+new SimpleDateFormat("yyyyMMdd").format(tempDate);

		File filePath = new File(dirUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File tempFile=new File(dirUrl + File.separator + fileName);
		faceZip.transferTo(tempFile);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileInputStream fileInputStream=null;
				try {
					fileInputStream = new FileInputStream(new File(dirUrl + File.separator + fileName));
					Boolean result=FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
		            ftpProperties.getPassword(),ftpProperties.getBasePath(),"",fileName,fileInputStream);
					log.info("["+fileName+"]上传结果："+result);
			        System.err.println("face...result..."+result);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(fileInputStream!=null){
						try {
							fileInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
		return new ReturnMsg(ReturnMsg.SUCCESS, ReturnMsg.SUCCESS_DESC, null, "1000");
	}

}
