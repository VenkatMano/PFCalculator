package com.apache.pfcalculator.datauploadservice.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apache.pfcalculator.datauploadservice.model.Employee;
import com.apache.pfcalculator.datauploadservice.service.EmployeeService;

@RestController
@RequestMapping("/upload-service")
public class UploadResource {
	
	@Autowired
	EmployeeService employeeService;
	
	@Value("${input.folder.path}")
	private String inputFolderPath;
	
	@PostMapping
	public ResponseEntity<Employee> uploadAJson(@RequestBody Employee employee)
	{
		//Stub will be updated soon
		Employee createdEmployee = employeeService.createEmployee(employee);
		if(createdEmployee!=null)
		{
			return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
		}		
		else
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/file")
	public ResponseEntity uploadZipFile(@RequestParam("file") MultipartFile file)
	{	
		try
		{
			InputStream fis = file.getInputStream();
			File inputFolder = new File(inputFolderPath);
			inputFolder.mkdirs();
			File zipFile = new File(inputFolder.getAbsolutePath()+File.separator+file.getOriginalFilename());
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);			
			IOUtils.copy(fis, fos);
			fos.close();
			fis.close();			
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
