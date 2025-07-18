package org.unilab.uniplan.coursegroup;

import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unilab.uniplan.coursegroup.dto.CourseGroupDto;

@Service
@RequiredArgsConstructor
public class CourseGroupService {

    private static final String COURSEGROUP_NOT_FOUND = "CourseGroup with ID {0} not found.";

    private final CourseGroupRepository courseGroupRepository;
    private final CourseGroupMapper courseGroupMapper;

    @Transactional
    public CourseGroupDto createCourseGroup(final CourseGroupDto courseGroupDTO) {
        final CourseGroup courseGroup = courseGroupMapper.toEntity(courseGroupDTO);
        return courseGroupMapper.toDTO(courseGroupRepository.save(courseGroup));
    }

    public Optional<CourseGroupDto> findCourseGroupById(final UUID id) {
        return courseGroupRepository.findById(id)
                                    .map(courseGroupMapper::toDTO);
    }

    public List<CourseGroupDto> findAll() {
        return courseGroupRepository.findAll()
                                    .stream().map(courseGroupMapper::toDTO).toList();
    }

    @Transactional
    public Optional<CourseGroupDto> updateCourseGroup(final UUID id,
                                                      final CourseGroupDto courseGroupDTO) {

        return courseGroupRepository.findById(id).map(
            existingCourseGroup -> {
                courseGroupMapper.updateEntityFromDTO(courseGroupDTO, existingCourseGroup);

                return courseGroupMapper.toDTO(courseGroupRepository.save(existingCourseGroup));
            });
    }

    @Transactional
    public void deleteCourseGroup(final UUID id) {
        final CourseGroup courseGroup = courseGroupRepository.findById(id)
                                                             .orElseThrow(() -> new RuntimeException(
                                                                 MessageFormat.format(
                                                                     COURSEGROUP_NOT_FOUND,
                                                                     id)));
        courseGroupRepository.delete(courseGroup);
    }
}
