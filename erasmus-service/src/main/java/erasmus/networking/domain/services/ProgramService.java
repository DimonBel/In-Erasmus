package erasmus.networking.domain.services;

import erasmus.networking.api.responses.ProgramResponse;
import erasmus.networking.domain.model.entity.Program;
import erasmus.networking.domain.model.mappers.ProgramMapper;
import erasmus.networking.domain.model.repository.ProgramRepository;
import erasmus.networking.domain.model.repository.specifications.ProgramSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramResponse getProgramsByStudentId(Long studentId) {
        var programs = programRepository.getProgramsByStudentId(studentId).orElseThrow(
                () -> new RuntimeException("Program not found")
        );

        return ProgramMapper.mapToProgramResponseFromProgramEntity.apply(programs);
    }

    public List<ProgramResponse> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(ProgramMapper.mapToProgramResponseFromProgramEntity)
                .toList();
    }

    public List<ProgramResponse> searchPrograms(String search, Pageable pageable) {
        ProgramSpecificationBuilder builder = new ProgramSpecificationBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }

        Specification<Program> spec = builder.build();
        Page<Program> students = programRepository.findAll(spec, pageable);

        return students.map(ProgramMapper.mapToProgramResponseFromProgramEntity).toList();
    }
}
