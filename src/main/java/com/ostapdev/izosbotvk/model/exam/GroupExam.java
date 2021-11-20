package com.ostapdev.izosbotvk.model.exam;

import com.ostapdev.izosbotvk.model.exam.Exam;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("group_exams")
public class GroupExam {
    @Id
    private BigInteger id;

    @NonNull
    private String group;

    @NonNull
    private String course;

    @NonNull
    private List<Exam> exams;
}
