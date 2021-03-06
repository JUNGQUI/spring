package com.jk.spring.builder;

import com.jk.spring.jkenum.JKEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEST_OBJ")
public class TestObject {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // lob 절 대 쓰 지 마
    // 뭔가 방법이 있을텐데, ResultSet extract 를 실패한다.
    // DB 머신에 따라 lob 이 되고 안되고 하더라...
    @Lob
    @Column(columnDefinition = "text")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TestObjectForRelation testObjectForRelation;

    @Enumerated(EnumType.STRING)
    private JKEnum jkEnum;

    public void changeContent(String name, String content) {
        // 예외에 대한 처리 및 실제 변경의 logic 이 도메인 내에 존재
        // 이에 따라 별도의 validation 및 exception 처리가 필요 없어지며,
        // 그러한 부분들에 대한 일관성 또한 이곳에서 유지가 가능하다.
        boolean isChanged = false;

        if (StringUtils.hasText(name)) {
            this.name = name;
            isChanged = true;
        }

        if (StringUtils.hasText(content)) {
            this.content = content;
            isChanged = true;
        }

        if (isChanged) {
            this.updatedDate = new Date();
        }
    }
}
