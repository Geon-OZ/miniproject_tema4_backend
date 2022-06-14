package com.sparta.miniproject_team4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public abstract class Timestamped {   //추상으로 달아둘것! 상속이 되어야만 쓸수있게 해줘야함.

    @CreatedDate //생성시간
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @LastModifiedDate //수정시간
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;
}
