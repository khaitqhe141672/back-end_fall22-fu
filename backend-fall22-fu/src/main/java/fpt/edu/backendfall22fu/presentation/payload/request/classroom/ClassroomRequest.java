package fpt.edu.backendfall22fu.presentation.payload.request.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequest {
    private Long hostID;
    private String classroomName;
    private Long status;
    private String code;
    private String description;
    private String urlPicture;
}
