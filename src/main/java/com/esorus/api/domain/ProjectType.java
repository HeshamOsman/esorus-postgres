package com.esorus.api.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A project type.
 */
@Entity
@Table(name = "project_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectType implements Serializable{
	  private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	    @SequenceGenerator(name = "sequenceGenerator")
	    private Long id;
	    
	    @Size(min = 1, max = 190)
	    @Column(name = "slug",length = 190, unique = true, nullable = false)
	    private String slug;
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}

			ProjectType drive = (ProjectType) obj;

			return Objects.equals(id, drive.id);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}
		
		@Override
		public String toString() {
			return "ProjectType{" + "id=" + id +"'}";
		}
}
