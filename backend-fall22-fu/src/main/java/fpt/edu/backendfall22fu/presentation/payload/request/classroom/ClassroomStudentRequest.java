package fpt.edu.backendfall22fu.presentation.payload.request.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomStudentRequest {

    @NotBlank
    private String code;
}
