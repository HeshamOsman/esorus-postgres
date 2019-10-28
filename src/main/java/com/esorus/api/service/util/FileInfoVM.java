package com.esorus.api.service.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FileInfoVM {

	private byte[] fileBytes;
	private String fileSystemName;
	private String displayName;
	private String fileContentType;
	private Long size;
	
	public FileInfoVM() {
	}
	
	public FileInfoVM(byte[] fileBytes, String fileSystemName, String displayName, String fileContentType,
			Long size) {
		this.fileBytes = fileBytes;
		this.fileSystemName = fileSystemName;
		this.displayName = displayName;
		this.fileContentType = fileContentType;
		this.size = size;
	}
	
	public FileInfoVM( String fileSystemName) {
		this.fileSystemName = fileSystemName;
		
	}
	
	public FileInfoVM( String systemFileName, String originalFileName, String fileContentType,
			Long size) {
		this(null,systemFileName,originalFileName,fileContentType,size);
	
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileSystemName() {
		return fileSystemName;
	}

	public void setFileSystemName(String fileSystemName) {
		this.fileSystemName = fileSystemName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	
	
	
}
