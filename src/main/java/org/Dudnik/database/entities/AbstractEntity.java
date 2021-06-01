package org.Dudnik.database.entities;

import org.hibernate.annotations.*;
import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    protected Integer id;

    @CreationTimestamp
    @Column( nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @UpdateTimestamp
    @Column( nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt ;
}