package be.riddler.v1.activity.adapter;

import be.riddler.v1.activity.adapter.mapper.ActivityMapper;
import be.riddler.v1.activity.adapter.repository.ActivityRepository;
import be.riddler.v1.activity.api.ActivityDetail;
import be.riddler.v1.activity.port.GetActivitiesOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * GetActivitiesOutAdapter
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor
class GetActivitiesOutAdapter implements GetActivitiesOutPort {
    private final ActivityRepository activityRepository;

    @Override
    public List<ActivityDetail> getActivities(UUID activityId) {
        return activityRepository.findAllByQuestionId(activityId)
                .stream()
                .map(ActivityMapper::fromActivityEntity)
                .toList();
    }
}
