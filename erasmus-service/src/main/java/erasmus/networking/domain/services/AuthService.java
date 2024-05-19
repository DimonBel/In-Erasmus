package erasmus.networking.domain.services;

import erasmus.networking.api.requests.student.StudentRequest;
import erasmus.networking.api.responses.jwt.JWT;
import erasmus.networking.api.responses.student.StudentWithJwtResponse;
import erasmus.networking.api.security.JWTUtil;
import erasmus.networking.domain.model.entity.Authority;
import erasmus.networking.domain.model.entity.Student;
import erasmus.networking.domain.model.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final StudentService studentService;

    public JWT authenticate(String email, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));
        var userDetails = (UserDetails) authentication.getPrincipal();

        Set<Authority> authorities =
                userDetails.getAuthorities().stream()
                        .map(
                                authority -> {
                                    Authority authority1 = new Authority();
                                    authority1.setName(authority.getAuthority());
                                    return authority1;
                                })
                        .collect(Collectors.toSet());

        return jwtUtil.generateJWT(email, authorities);
    }

    public StudentWithJwtResponse registerUser(StudentRequest studentRequest) {
        Student student = studentService.saveStudent(studentRequest);

        if (student.getAuthorities() == null) {
            log.error("Authorities for the student {} cannot be null", student.getEmail());
            throw new IllegalStateException("Authorities must be set for a student");
        }

        JWT jwt = jwtUtil.generateJWT(student.getEmail(), student.getAuthorities());
        log.info("Token generated: {}", jwt.getToken());

        return StudentMapper.mapStudentToStudentWithJwtResponse.apply(student, jwt);
    }
}
