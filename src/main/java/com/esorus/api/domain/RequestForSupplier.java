package com.esorus.api.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A request for supplier.
 */
@Entity
@Table(name = "request_for_supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RequestForSupplier implements Serializable{
	  private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	    @SequenceGenerator(name = "sequenceGenerator")
	    private Long id;
	    @NotNull
	    @ManyToOne
	    @JoinColumn(name = "type_of_work_id")
	    private TypeOfWorkNeeded typeOfWorkNeeded;
	    @NotNull
	    @ManyToOne
	    @JoinColumn(name = "project_type_id")
	    private ProjectType projectType;
	    @NotNull
	    @ManyToOne
	    @JoinColumn(name = "project_phase_id")
	    private ProjectPhase projectPhase;
	    @NotNull
	    @ManyToOne
	    @JoinColumn(name = "user_id",nullable = false)
	    private User user;
	    
	    @ManyToOne
	    @JoinColumn(name = "pic_uplaods_id")
	    private Uploads uploadedPic;
	    
	    @ManyToOne
	    @JoinColumn(name = "boq_uplaods_id")
	    private Uploads uploadedBOQ;
	    @NotNull
	    @Column(name="delivery_date")
	    private LocalDate deliveryDate;
	    
	    @Column(name="detailes")
	    private String detailes;
	    
	    @Column(name="quantity")
	    private String quantity;
	    
	    @Column(name="boq")
	    private Boolean boq;
	    
	    @Column(name="phone_number")
	    private String phoneNumber;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TypeOfWorkNeeded getTypeOfWorkNeeded() {
			return typeOfWorkNeeded;
		}

		public void setTypeOfWorkNeeded(TypeOfWorkNeeded typeOfWorkNeeded) {
			this.typeOfWorkNeeded = typeOfWorkNeeded;
		}

		public ProjectType getProjectType() {
			return projectType;
		}

		public void setProjectType(ProjectType projectType) {
			this.projectType = projectType;
		}

		public ProjectPhase getProjectPhase() {
			return projectPhase;
		}

		public void setProjectPhase(ProjectPhase projectPhase) {
			this.projectPhase = projectPhase;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Uploads getUploadedPic() {
			return uploadedPic;
		}

		public void setUploadedPic(Uploads uploadedPic) {
			this.uploadedPic = uploadedPic;
		}

		public Uploads getUploadedBOQ() {
			return uploadedBOQ;
		}

		public void setUploadedBOQ(Uploads uploadedBOQ) {
			this.uploadedBOQ = uploadedBOQ;
		}

		public LocalDate getDeliveryDate() {
			return deliveryDate;
		}

		public void setDeliveryDate(LocalDate deliveryDate) {
			this.deliveryDate = deliveryDate;
		}

		public String getDetailes() {
			return detailes;
		}

		public void setDetailes(String detailes) {
			this.detailes = detailes;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		public Boolean getBoq() {
			return boq;
		}

		public void setBoq(Boolean boq) {
			this.boq = boq;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
	    
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}

			RequestForSupplier drive = (RequestForSupplier) obj;

			return Objects.equals(id, drive.id);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}
		
		@Override
		public String toString() {
			return "RequestForSupplier{" + "id=" + id +"'}";
		}
}
