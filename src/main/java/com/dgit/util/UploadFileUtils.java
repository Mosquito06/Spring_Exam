package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		File dirPath = new File(uploadPath);		
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalName;
		
		// 년월일 폴더 생성
		// 한 폴더에 저장할 수 있는 용량이 제한되어 있으므로, 년월일 폴더를 만들도록 함
		String savedPath = calPath(uploadPath);		
		
		// c:/zzz/upload/2018/03/19 아래에 해당 파일을 저장하라는 의미
		File target = new File(uploadPath + savedPath, savedName);
		
		try {
			FileCopyUtils.copy(fileData, target);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Thumbnail 처리, 서버의 부하를 줄이기 위해 작은 이미지를 만들어서 화면에 보이게 함
		String thumbName = makeThumbnail(uploadPath, savedPath, savedName);
		
		return thumbName;
		/*return savedPath + "/" + savedName;*/ //원본 이미지를 반환하는 것에서 썸네일이 반환되도록 변경
	}
	
	public static void makeDir(String uploadPath, String...paths){
		for(String path : paths){
			File dirPath = new File(uploadPath + path);
			if(!dirPath.exists()){
				dirPath.mkdirs();
			}
		}
	}
	
	private static String calPath(String uploadPath){
		Calendar cal = Calendar.getInstance();
		
		String yearPath = "/" + cal.get(Calendar.YEAR);
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH) + 1);
		String dataPath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, dataPath);
		
		return dataPath; // /2018/03/19 가 반환됨
	}
	
	private static String makeThumbnail(String uploadPath, String path, String filename) throws IOException{
		// 원본 이미지를 읽어드림
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, filename));
		
		// 원본 이미지를 리사이징 함.
		// 높이를 맞춤. 100px, 나머지는 자동
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		// 원본이미지에 썸네일 이미지가 덮어쓰는 것을 방지하기 위해 경로 내에 이름을 변경
		String thumbnailName = uploadPath + path + "/s_" + filename;
		
		File newFile = new File(thumbnailName);
		String formatName = filename.substring(filename.lastIndexOf(".") + 1);
		
		// Thumbnail 경로/타입/resizing 된 이미지를 넘김
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		// Thumbnail이 포함된 경로를 반환		
		return thumbnailName.substring(uploadPath.length());
	}
	
	public static void deleteImg(String filename){
		String originalFile = filename.substring(0, filename.indexOf("s_")) + filename.substring(filename.indexOf("s_") + 2);

		try{
			System.gc(); // 가비지 컬렉터 호출
			File del = new File(filename);
			if(del.exists()){
				del.delete();
			}
			
			File delOriginal = new File(originalFile);
			if(delOriginal.exists()){
				delOriginal.delete();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
