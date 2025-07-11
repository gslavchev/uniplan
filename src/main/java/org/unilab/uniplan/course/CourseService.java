package org.unilab.uniplan.course;

import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unilab.uniplan.course.dto.CourseDto;

@Service
@RequiredArgsConstructor
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Course with ID {0} not found.";

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseDto createCourse(final CourseDto courseDTO) {
        final Course course = courseMapper.toEntity(courseDTO);
        return courseMapper.toDTO(courseRepository.save(course));
    }

    public Optional<CourseDto> findCourseById(final UUID id) {
        return courseRepository.findById(id)
                               .map(courseMapper::toDTO);
    }

    public List<CourseDto> findAll() {
        return courseRepository.findAll()
                               .stream().map(courseMapper::toDTO).toList();
    }

    @Transactional
    public Optional<CourseDto> updateCourse(final UUID id, final CourseDto courseDTO) {
        return courseRepository.findById(id).map(
            existingCourse -> {
                courseMapper.updateEntityFromDTO(courseDTO, existingCourse);

                return courseMapper.toDTO(courseRepository.save(existingCourse));
            });
    }

    @Transactional
    public void deleteCourse(final UUID id) {
        final Course course = courseRepository.findById(id)
                                              .orElseThrow(() -> new RuntimeException(
                                                  MessageFormat.format(COURSE_NOT_FOUND, id)));
        courseRepository.delete(course);
    }
}
