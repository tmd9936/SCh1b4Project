package com.h1b4.www.youtube.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketGetOption;
import com.google.cloud.storage.StorageOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.MultipartContent.Part;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;


import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAsync
@Controller
public class AsyncConfig implements AsyncConfigurer {
	
	 
    /** 기타 Thread */
    private static ThreadPoolTaskExecutor EXECUTOR_ETC;
    /** 기타 기본 Thread 수 */
    private static int TASK_ETC_CORE_POOL_SIZE = 5;
    /** 기타 최대 Thread 수 */
    private static int TASK_ETC_MAX_POOL_SIZE = 10;
    /** 기타 QUEUE 수 */
    private static int TASK_ETC_QUEUE_CAPACITY = 0;

	@Override
	public Executor getAsyncExecutor() {
		EXECUTOR_SMLT = new ThreadPoolTaskExecutor();
        EXECUTOR_SMLT.setCorePoolSize(TASK_SMLT_CORE_POOL_SIZE);
        EXECUTOR_SMLT.setMaxPoolSize(TASK_SMLT_MAX_POOL_SIZE);
        EXECUTOR_SMLT.setQueueCapacity(TASK_SMLT_QUEUE_CAPACITY);
        EXECUTOR_SMLT.setBeanName("executorSmlt");
        EXECUTOR_SMLT.initialize();
        return EXECUTOR_SMLT;


	}
	
	 /**
     * 시뮬레이션 Thread 등록 가능 여부
     *
     * @return 실행중인 task 개수가 최대 개수(max + queue)보다 크거나 같으면 false
     */
    public static boolean isSmltTaskExecute() {
        boolean rtn = true;
 
        // 실행중인 task 개수가 최대 개수(max + queue)보다 크거나 같으면 false
        if (EXECUTOR_SMLT.getActiveCount() >= (TASK_SMLT_MAX_POOL_SIZE + TASK_SMLT_QUEUE_CAPACITY)) {
            rtn = false;
        }
 
        return rtn;
    }	

    /**
     * 시뮬레이션 Thread 등록 가능 여부
     *
     * @param createCnt : 생성 개수
     * @return 실행중인 task 개수 + 실행할 개수가 최대 개수(max + queue)보다 크거나 같으면 false
     */
    public static boolean isSmltTaskExecute(int createCnt) {
        boolean rtn = true;
 
        // 실행중인 task 개수 + 실행할 개수가 최대 개수(max + queue)보다 크거나 같으면 false
        if ((EXECUTOR_SMLT.getActiveCount() + createCnt) >= (TASK_SMLT_MAX_POOL_SIZE + TASK_SMLT_QUEUE_CAPACITY)) {
            rtn = false;
        }
 
        return rtn;
    }

    
    @Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		
		return null;
	}
   

    @Bean(name = "executorEtc")
    @Qualifier
    public Executor taskExecutorEtc() {
        EXECUTOR_ETC = new ThreadPoolTaskExecutor();
        EXECUTOR_ETC.setCorePoolSize(TASK_ETC_CORE_POOL_SIZE);
        EXECUTOR_ETC.setMaxPoolSize(TASK_ETC_MAX_POOL_SIZE);
        EXECUTOR_ETC.setQueueCapacity(TASK_ETC_QUEUE_CAPACITY);
        EXECUTOR_ETC.setBeanName("executorEtc");
        EXECUTOR_ETC.initialize();
        return EXECUTOR_ETC;
    }

    /**
     * 기타 Thread 등록 가능 여부
     *
     * @return 실행중인 task 개수가 최대 개수(max + queue)보다 크거나 같으면 false
     */
    public static boolean isEtcTaskExecute() {
        boolean rtn = true;
 
        // 실행중인 task 개수가 최대 개수(max + queue)보다 크거나 같으면 false
        if (EXECUTOR_ETC.getActiveCount() >= (TASK_ETC_MAX_POOL_SIZE + TASK_ETC_QUEUE_CAPACITY)) {
            rtn = false;
        }
 
        return rtn;
    }
 
    /**
     * 기타 Thread 등록 가능 여부
     *
     * @param createCnt : 생성 개수
     * @return 실행중인 task 개수 + 실행할 개수가 최대 개수(max + queue)보다 크거나 같으면 false
     */
    public static boolean isEtcTaskExecute(int createCnt) {
        boolean rtn = true;
 
        // 실행중인 task 개수 + 실행할 개수가 최대 개수(max + queue)보다 크거나 같으면 false
        if ((EXECUTOR_ETC.getActiveCount() + createCnt) >= (TASK_ETC_MAX_POOL_SIZE + TASK_ETC_QUEUE_CAPACITY)) {
            rtn = false;
        }
 
        return rtn;
    }


	private static ThreadPoolTaskExecutor EXECUTOR_SMLT;
	
	private static int TASK_SMLT_CORE_POOL_SIZE = 2;
	
	private static int TASK_SMLT_MAX_POOL_SIZE = 2;
	
	private static int TASK_SMLT_QUEUE_CAPACITY = 0;
	
	
	
		

		
		/*Bucket bucket = storage.get(bucketName, BucketGetOption.userProject(bucketName));
		BucketSnippets bucketSnippets = new BucketSnippets(bucket);
		Blob blob = bucketSnippets.createBlobFromByteArray("123.flac");
		
		
		CloudStorageHelper cloudStorageHelper = new CloudStorageHelper();
		
		cloudStorageHelper.uploadFile(new File("\tmp\test\123.flac"), bucketName);*/
		
		/*final Part filePart = req.getPart("file");
	    final String fileName = filePart.getSubmittedFileName();

	    // Modify access list to allow all users with link to read file
	    List<Acl> acls = new ArrayList<>();
	    acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
	    // the inputstream is closed by default, so we don't need to close it here
	    Blob blob =
	        storage.create(
	            BlobInfo.newBuilder(BUCKET_NAME, fileName).setAcl(acls).build(),
	            filePart.getInputStream());

	    // return the public download link
	    resp.getWriter().print(blob.getMediaLink());*/
		

		/*try(OutputStream os = ((WritableResource) gcsFile).getOutputStream()){
		os.write(Files.readAllBytes(Paths.get("/tmp/test/123.flac")));
		*/
		
	
		/*
		InputStream content = new ByteArrayInputStream("Hello, World!".getBytes("UTF-8));
		
		Blob blob = bucket.create(blobName, content, "text/plain");*/
		
	/*	
		
		CloudStorageHelper csh = new CloudStorageHelper();
		String path = "/tmp/test/123.flac";
		
		File file = new File(path);
		*/
		/*Path path2 = Paths.get("/tmp/test/123.flac");
		String name = "123.flac";
		String originalFileName = "file.txt";
		String contentType = "";
		byte[] content = null;
		try{
			content = Files.readAllBytes(arg0);
		}*/
		
		
}
