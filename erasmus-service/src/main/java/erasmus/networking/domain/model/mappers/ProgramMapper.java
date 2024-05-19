package erasmus.networking.domain.model.mappers;

import erasmus.networking.api.responses.ProgramResponse;
import erasmus.networking.domain.model.entity.Program;

import java.util.function.Function;

public class ProgramMapper {

    private ProgramMapper() {
    }

    public static final Function<Program, ProgramResponse> mapToProgramResponseFromProgramEntity =
            program -> {
                ProgramResponse programResponse = new ProgramResponse();
                programResponse.setId(program.getId());
                programResponse.setName(program.getName());
                programResponse.setDescription(program.getDescription());
                programResponse.setLink(program.getLink());
                programResponse.setAbbreviation(program.getAbbreviation());
                programResponse.setStudyField(program.getStudyField());
                programResponse.setCreatedAt(program.getCreatedAt().toString());
                programResponse.setTripDate(program.getTripDate().toString());
                programResponse.setApplicationDeadline(program.getApplicationDeadline().toString());
                return programResponse;
            };
}
