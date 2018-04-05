/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.h1b4.www.youtube.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.ServletException;

import org.apache.log4j.lf5.util.Resource;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;



@Service
public class CloudStorageHelper {
	
	@Value("speechstorage_h1b4") 
	private String bucketName;
	
	@Value(value="classpath:H1B4-a8035531e67e.json")
	public Resource accountResource;
	
	
	
	private static Storage storage = null;
	
	// [START init]
	  static {
	  //  storage = StorageOptions.getDefaultInstance().getService();
	    
	    GoogleCredentials credentials = ComputeEngineCredentials.create();
	    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	  }
	  // [END init]

	  // [START uploadFile]
	 
	  /**
	   * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
	   * environment variable, appending a timestamp to end of the uploaded filename.
	   */
	  @SuppressWarnings("deprecation")
	  public String uploadFile(File file, final String bucketName,String filename)
	      throws IOException, ServletException {
	    checkFileExtension(file.getName());

	   /* DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
	    DateTime dt = DateTime.now(DateTimeZone.UTC);
	    String dtString = dt.toString(dtf);
	    final String fileName = file.getName() + dtString;*/

	    // the inputstream is closed by default, so we don't need to close it here
	    BlobInfo blobInfo =
	        storage.create(
	            BlobInfo
	                .newBuilder(bucketName, filename)
	                // Modify access list to allow all users with link to read file
	                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
	                .build(),
	            Files.newInputStream(Paths.get("/tmp/test/"+filename+".flac")));
	    // return the public download link
	    return blobInfo.getMediaLink();
	  }
	  // [END uploadFile]

	  // [START checkFileExtension]
	  /**
	   * Checks that the file extension is supported.
	   */
	  private void checkFileExtension(String fileName) throws ServletException {
	    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
	      String[] allowedExt = { ".flac" };
	      for (String ext : allowedExt) {
	        if (fileName.endsWith(ext)) {
	          return;
	        }
	      }
	      throw new ServletException("file must be an flac");
	    }
	  }
	  // [END checkFileExtension]
	  
	/**
	 * 구글 스토리지에 파일 넣기
	 * @param filename
	 * @throws Exception
	 */
	public void insertFile(String filename) throws Exception {
		Storage storage = StorageOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream()))
				.build()
				.getService();
		
		BlobInfo blobInfo = storage.create( BlobInfo.newBuilder(bucketName, filename) // Modify access list to allow all users with link to read file 
				.setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))) 
				.build()
				, new FileInputStream(new File("c:/tmp/test/"+filename))); 
		
		// return the public download link 
		Files.deleteIfExists(new File("c:/tmp/test/"+filename).toPath());

	}
}
