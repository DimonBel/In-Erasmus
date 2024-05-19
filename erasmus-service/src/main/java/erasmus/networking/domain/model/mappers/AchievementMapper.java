package erasmus.networking.domain.model.mappers;

import erasmus.networking.api.responses.AchievementResponse;
import erasmus.networking.domain.model.entity.Achievements;

import java.util.function.Function;

public class AchievementMapper {

    private AchievementMapper() {
    }

    public static final Function<Achievements, AchievementResponse> mapAchievementToAchievementResponse = achievements -> {
        AchievementResponse achievementResponse = new AchievementResponse();
        achievementResponse.setId(achievements.getId());
        achievementResponse.setName(achievements.getName());
        achievementResponse.setDescription(achievements.getDescription());
        achievementResponse.setImageUrl(achievements.getImageUrl());
        return achievementResponse;
    };

}
