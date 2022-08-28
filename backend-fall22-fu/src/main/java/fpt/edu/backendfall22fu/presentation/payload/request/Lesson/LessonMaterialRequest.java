package fpt.edu.backendfall22fu.presentation.payload.request.Lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class LessonMaterialRequest {

    private String type;
}
