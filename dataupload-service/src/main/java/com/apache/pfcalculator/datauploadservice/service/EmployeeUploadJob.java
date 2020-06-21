package com.apache.pfcalculator.datauploadservice.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.datauploadservice.model.Employee;
import com.apache.pfcalculator.datauploadservice.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeUploadJob {
	
	@Value("${input.folder.path}")
	private String inputFolderPath;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private EmployeeServiceImpl employeeService;
	
	Logger logger = LoggerFactory.getLogger(EmployeeUploadJob.class);
	
	private static final String PF_STARTDATE_STRING = "pfStartDate";
	
	@Scheduled(initialDelay=5000, fixedDelay=5000)
	public void processZipFile()
	{
		File file = new File(inputFolderPath);
		try {
			processAllFiles(file.listFiles());
		} catch (IOException e) {
			logger.error("An exception occurred while reading the file -{} ", e.getMessage());
		}
		catch(Exception e)
		{
			logger.error("An exception occured during processing of filees - {}", e.getLocalizedMessage());
		}
	}
	
	private void processAllFiles(File[] listOfFiles) throws Exception
	{
		for(File file: listOfFiles)
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			try
			{
				while(entries.hasMoreElements())
				{
					try
					{
						ZipEntry zipEntry = entries.nextElement();
						InputStream in = zipFile.getInputStream(zipEntry);
						String json = IOUtils.toString(in);
						JSONObject jsonObject = new JSONObject(json);				
						String jsonDate = jsonObject.getString(PF_STARTDATE_STRING);								
						jsonObject.put(PF_STARTDATE_STRING, CommonUtils.convertJsonDateToAcceptedFormat(jsonDate));
						Employee employee = objectMapper.readValue(jsonObject.toString(), Employee.class);
						employeeService.createEmployee(employee);
					}
					catch(Exception e)
					{
						logger.error("An exception occurred but continuing for other values - {}", e.getMessage());
					}
				}
			}	
			finally{
				zipFile.close();
				Files.delete(file.toPath());
			}			
		}
	}

}
