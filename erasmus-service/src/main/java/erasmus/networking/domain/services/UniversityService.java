package erasmus.networking.domain.services;

import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;
import lombok.RequiredArgsConstructor;
import erasmus.networking.domain.model.entity.University;
import erasmus.networking.domain.model.mappers.UniversityMapper;
import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;
import erasmus.networking.domain.model.repository.UniversityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
  private final UniversityRepository universityRepository;

  @PreAuthorize("hasRole('ADMIN')")
  public UniversityResponse createUniversity(UniversityCreateRequest request) {
    universityRepository
        .findUniversityRecordByName(request.name())
        .ifPresent(
            university -> {
              throw new IllegalArgumentException(
                  "University with name " + request.name() + " already exists");
            });

    University university = UniversityMapper.mapToUniversity.apply(request);

    University savedUniversity = universityRepository.save(university);

    return UniversityMapper.mapToUniversityResponse.apply(savedUniversity);
  }

  public Page<UniversityResponse> getAllUniversities(Pageable pageable) {
    return universityRepository.findAll(pageable).map(UniversityMapper.mapToUniversityResponse);
  }

  @PreAuthorize("hasRole('ADMIN')")
  public void updateUniversity(Long id, UniversityCreateRequest universityCreateRequest) {
    University university =
        universityRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("University with id " + id + " not found"));
    university.setName(universityCreateRequest.name());
    universityRepository.save(university);
  }

  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUniversity(Long id) {
    if (!universityRepository.existsById(id)) {
      throw new IllegalArgumentException("University with id " + id + " not found");
    }
    universityRepository.deleteById(id);
  }

  public UniversityResponse getUniversityById(Long id) {
    return universityRepository
        .findUniversityRecordById(id)
        .orElseThrow(() -> new IllegalArgumentException("University with id " + id + " not found"));
  }
}
