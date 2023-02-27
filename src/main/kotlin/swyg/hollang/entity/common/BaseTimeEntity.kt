package swyg.hollang.entity.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity (

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false,
        columnDefinition="DATETIME(0) default CURRENT_TIMESTAMP")
    var createdAt: ZonedDateTime? = ZonedDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false,
        columnDefinition="DATETIME(0) default CURRENT_TIMESTAMP")
    var updatedAt: ZonedDateTime? = ZonedDateTime.now()
)