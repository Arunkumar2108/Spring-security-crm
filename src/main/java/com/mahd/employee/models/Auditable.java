package com.mahd.employee.models;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

    @CreatedBy
    private U createdBy;

    @LastModifiedBy
    private U lastModifiedBy;
    
    
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;
    
}
