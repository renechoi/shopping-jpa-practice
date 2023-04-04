package ssipgeukbbok.shoppingjpapractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public String uploadFiles(String uploadPath, String originalFileName, byte[] fileData) throws Exception{

        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fileOutputStream = new FileOutputStream(fileUploadFullUrl);
        fileOutputStream.write(fileData);
        fileOutputStream.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if (deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제 완료");
        }
    }



}
