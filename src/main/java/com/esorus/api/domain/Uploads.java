package com.esorus.api.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.annotation.Signed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "uploads")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Uploads implements Serializable{
	  private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	    @SequenceGenerator(name = "sequenceGenerator")
	    private Long id;
	    @JsonIgnore
	    @NotNull
	    @Lob
	    @Column(name = "content", columnDefinition="BLOB",nullable=false)
	    private byte[] content;
	    
	    @Column(name="upload_date")
	    private ZonedDateTime uploadDate;
	    
	    @Column(name="data_type")
	    private String dataType;
	    
	    @Column(name="file_name")
	    private String fileName;
	    
	    @NotNull
	    @Size(max = 190)
	    @Column(name="saved_file_name")
	    private String savedFileName;
	    
	    @Column(name="file_type")
	    private String fileType;
	    @JsonIgnore
	    @NotNull
	    @ManyToOne
	    @JoinColumn(name = "user_id",nullable = false)
	    private User user;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public byte[] getContent() {
			return content;
		}

		public void setContent(byte[] content) {
			this.content = content;
		}

		public ZonedDateTime getUploadDate() {
			return uploadDate;
		}

		public String getSavedFileName() {
			return savedFileName;
		}

		public void setSavedFileName(String savedFileName) {
			this.savedFileName = savedFileName;
		}

		public void setUploadDate(ZonedDateTime uploadDate) {
			this.uploadDate = uploadDate;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	    
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}

			Uploads drive = (Uploads) obj;

			return Objects.equals(id, drive.id);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}
		
		@Override
		public String toString() {
			return "Uploads{" + "id=" + id +"'}";
		}
}
