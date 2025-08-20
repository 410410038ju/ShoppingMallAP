package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Tag(name = "檔案下載")
@RestController
@RequestMapping("/api/files")
public class FileDownloadController {

    @Operation(summary = "下載檔案", description = "路徑為C:/Users/41041/OneDrive/桌面/testest/")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) {
        // 檔案路徑
        File file = new File("C:\\Users\\41041\\OneDrive\\桌面\\testest\\" + filename);

        // 檔案不存在，則回傳404
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Resource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build()); // 為下載檔案
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 二進位資料

        return ResponseEntity.ok() // HTTP狀態碼；200 OK
                .headers(headers)
                .body(resource);
    }
}
