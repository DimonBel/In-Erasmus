package erasmus.networking.api.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchievementResponse {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;
}
