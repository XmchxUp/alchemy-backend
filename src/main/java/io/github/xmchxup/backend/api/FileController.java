package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.model.DBFile;
import io.github.xmchxup.backend.service.DBFileStorageService;
import io.github.xmchxup.backend.vo.UploadFileResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "上传下载")
@RequestMapping("/api/file")
@RestController
@Validated
public class FileController {
    @Autowired
    private DBFileStorageService dbFileStorageService;

    @ApiOperation("上传单个文件")
    @PostMapping("/uploadFile")
    @ApiImplicitParam(name = "file", value = "单个文件", required = true, dataType = "__file")
    public UploadFileResponseVo uploadFile(@RequestParam("file")MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        return new UploadFileResponseVo(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @ApiOperation("上传多个文件")
    @PostMapping("/uploadMultipleFiles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件", allowMultiple = true, dataType = "__file")
    })
    public List<UploadFileResponseVo> uploadMultipleFiles(@RequestParam(value = "files", required = true) MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @ApiOperation("下载")
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        DBFile dbFile = dbFileStorageService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
