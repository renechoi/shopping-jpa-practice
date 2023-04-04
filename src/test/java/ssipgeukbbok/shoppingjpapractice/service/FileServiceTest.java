package ssipgeukbbok.shoppingjpapractice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    public static final String DELETE_FILE_PATH = "src/test/resources/testFiles/jeans1.jpg";
    @Autowired
    private FileService fileService;

    @Test
    @DisplayName("파일 업로드 테스트")
    void testUploadFile() throws Exception {
        // Given
        String uploadPath = "src/test/resources/testFiles";
        String originalFileName = "test.txt";
        byte[] fileData = "test file data".getBytes();

        // When
        String savedFileName = fileService.uploadFiles(uploadPath, originalFileName, fileData);

        // Then
        assertThat(savedFileName).isNotNull();
    }



    @AfterEach
    void tearDown() throws IOException {
        restoreFile(DELETE_FILE_PATH+".backup", DELETE_FILE_PATH);
    }

    @Test
    @DisplayName("파일 삭제 테스트")
    void testDeleteFile() throws Exception {
        // Given
        String filePath = DELETE_FILE_PATH;

        // When
        copyFile(filePath, filePath + ".backup");        // 파일 복사
        fileService.deleteFile(filePath);

        // Then
        assertThat(new File(filePath).exists()).isFalse();
    }



    private void copyFile(String source, String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    private void restoreFile(String source, String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }


}
