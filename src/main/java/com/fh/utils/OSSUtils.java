package com.fh.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;

public class OSSUtils {

    public static String uploadFile(File file){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com/";
        String endpointPath = "oss-cn-qingdao.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GE3eAG5rLt4tQXfJBBW";
        String accessKeySecret = "E5dOwRYXhiMXlvf7zwY0cAnFDQXi8S";
        String bucketName="1908lsc";
// 创建OSSClient实例。                  路径   id  秘钥
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建PutObjectRequest对象。  oss的存储文件名  上传文件名  文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(),file);

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传文件。
        PutObjectResult putOjbectResult = ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();
    return "http://"+bucketName+"."+endpointPath+"/"+file.getName();
    }
}
