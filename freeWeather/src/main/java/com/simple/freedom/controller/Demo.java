package com.simple.freedom.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.simple.freedom.common.aop.BaseController;
import com.simple.freedom.common.util.ExcelHelper;
import com.simple.freedom.common.util.activeMqHelper;
import com.simple.freedom.common.util.activeMqHelper2;
import com.simple.freedom.test.beans.User;

@Controller
@RequestMapping("/demo")
public class Demo extends BaseController {

	/**
	 * 普通下载
	 * 
	 * @throws IOException
	 */
	@RequestMapping("download1")
	public void download1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ URLEncoder.encode("普通下载.txt", "utf-8"));// 设置文件名

			File fileTemp = new File("D:/普通下载.txt");
			fileTemp.createNewFile();
			fis = new FileInputStream(fileTemp);
			os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				os.write(buffer);
			}
		} catch (Exception e) {
			logger.error(this.getClass() + ":" + e);
		} finally {
			fis.close();
			os.close();
		}
	}
}
