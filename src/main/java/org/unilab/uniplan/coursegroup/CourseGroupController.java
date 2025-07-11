package org.unilab.uniplan.coursegroup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.unilab.uniplan.coursegroup.dto.CourseGroupDto;
import org.unilab.uniplan.coursegroup.dto.CourseGroupRequestDto;
import org.unilab.uniplan.coursegroup.dto.CourseGroupResponseDto;

@RestController
@RequestMapping("/courseGroups")
@RequiredArgsConstructor
public class CourseGroupController {

    private static final String COURSEGROUP_NOT_FOUND = "CourseGroup with ID {0} not found.";

    private final CourseGroupService courseGroupService;
    private final CourseGroupMapper courseGroupMapper;

    @PostMapping
    public ResponseEntity<CourseGroupResponseDto> addCourseGroup(@RequestBody
                                                                 @Valid @NotNull final CourseGroupRequestDto courseGroupRequestDTO) {
        final CourseGroupDto courseGroupDTO = courseGroupMapper.toInnerDTO(courseGroupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(courseGroupMapper.toResponseDTO(courseGroupService.createCourseGroup(
                                 courseGroupDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseGroupResponseDto> getCourseGroup(@PathVariable @NotNull final UUID id) {
        return ResponseEntity.ok(courseGroupMapper.toResponseDTO(courseGroupService.findCourseGroupById(
                                                                                       id)
                                                                                   .orElseThrow(() -> new ResponseStatusException(
                                                                                       HttpStatus.NOT_FOUND,
                                                                                       MessageFormat.format(
                                                                                           COURSEGROUP_NOT_FOUND,
                                                                                           id)))));
    }

    @GetMapping
    public List<CourseGroupResponseDto> getAllCourseGroups() {
        return courseGroupMapper.toResponseDTOList(courseGroupService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseGroupResponseDto> updateCourseGroup(@PathVariable @NotNull final UUID id,
                                                                    @RequestBody @NotNull @Valid final CourseGroupRequestDto courseGroupRequestDTO) {
        final CourseGroupDto courseGroupDTO = courseGroupMapper.toInnerDTO(courseGroupRequestDTO);
        return ResponseEntity.ok(courseGroupMapper.toResponseDTO(courseGroupService.updateCourseGroup(
                                                                                       id,
                                                                                       courseGroupDTO)
                                                                                   .orElseThrow(() -> new ResponseStatusException(
                                                                                       HttpStatus.NOT_FOUND,
                                                                                       MessageFormat.format(
                                                                                           COURSEGROUP_NOT_FOUND,
                                                                                           id)))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseGroup(@PathVariable @NotNull UUID id) {
        courseGroupService.deleteCourseGroup(id);
        return ResponseEntity.noContent().build();
    }
}
