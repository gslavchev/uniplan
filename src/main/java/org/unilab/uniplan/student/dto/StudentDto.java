package org.unilab.uniplan.student.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record StudentDto(
    UUID id,
    @NotNull
    @Size(max = 100)
    String firstName,
    @NotNull
    @Size(max = 100)
    String lastName,
    @Size(max = 40)
    String facultyNumber,
    @NotNull
    UUID courseId
) {

}
