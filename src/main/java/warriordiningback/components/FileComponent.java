package warriordiningback.components;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileComponent {

	  private Map<String, Object> resultMap;
	  private String lastPath = "/upload";
	  private String middlePath = "/src/main/resources/static";
	  private String getRootPath() {return new File("").getAbsolutePath();}
	  private String getCurrnetDatePath() {return "/".concat(new SimpleDateFormat("yyyyMMdd").format(new Date()));}
	  private String getName(MultipartFile file) {return file.getOriginalFilename();}
	  private String setName() {return Long.toString(System.nanoTime());}
	  private String getExtension(MultipartFile file) {
	    String contentType = file.getContentType();
	    String name = getName(file);
	    String originalFileExtension = "";
	    if (!ObjectUtils.isEmpty(contentType)){
	        if(contentType.contains("image/jpeg")){originalFileExtension = ".jpg";}
	        else if(contentType.contains("image/png")){originalFileExtension = ".png";}
	        else if(contentType.contains("image/gif")){originalFileExtension = ".gif";}
	        else if(name.lastIndexOf(".") > 0){originalFileExtension = name.substring(name.lastIndexOf("."), name.length());}
	    }
	    return originalFileExtension;
	  }

	  public Map<String, Object> setFile(MultipartFile multipartFile) {
	    resultMap = new HashMap<String, Object>();
	    resultMap.put("Root", getRootPath());
	    resultMap.put("Middle", middlePath);
	    resultMap.put("LastPath", lastPath);
	    resultMap.put("CurrnetDate", getCurrnetDatePath());
	    resultMap.put("Name", getName(multipartFile));
	    resultMap.put("NewName", setName());
	    resultMap.put("Extension", getExtension(multipartFile));
	    return resultMap;
	  }

	  public String upload(MultipartFile multipartFile) {
	    String path = getRootPath() + middlePath + lastPath + getCurrnetDatePath();
	    String fileName = getName(multipartFile);
	    File file = new File(path + "/" + fileName);
	    if(!file.exists()){
	      file.mkdirs();
	    }
	    try {
	      multipartFile.transferTo(file);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return lastPath + getCurrnetDatePath() + "/" + fileName;
	  }

	  public String upload2(MultipartFile multipartFile) {
	    String url = lastPath.concat(getCurrnetDatePath()).concat("/").concat(setName()).concat(getExtension(multipartFile));
	    String newPath = getRootPath().concat(middlePath).concat(url);
	    try {
	      File file = new File(newPath);
	      if(!file.exists()){
	    	  file.mkdirs();
	      }
	      multipartFile.transferTo(file);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return url;
	  }

	  public ResponseEntity<?> getFile(String url) {
	    try {
	      String path = getRootPath().concat(middlePath).concat(url);
	      File file = new File(path);
	      return ResponseEntity.ok()
	        .contentLength(file.length())
	        .contentType(MediaType.parseMediaType("image/png"))
	        .body(new InputStreamResource(new FileInputStream(file)));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.notFound().build();
	    }
	  }
	
}
