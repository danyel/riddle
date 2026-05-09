package be.riddler.v1.activity.adapter;

import be.riddler.v1.activity.adapter.mapper.ActivityMapper;
import be.riddler.v1.activity.adapter.repository.ActivityRepository;
import be.riddler.v1.activity.api.ActivityDetail;
import be.riddler.v1.activity.api.CreateActivity;
import be.riddler.v1.activity.port.CreateActivityOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreateActivityOutAdapter
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class CreateActivityOutAdapter implements CreateActivityOutPort {
    private final ActivityRepository activityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ActivityDetail create(CreateActivity createActivity) {
        var activityEntity = ActivityMapper.fromCreateActivity(createActivity);
        activityRepository.save(activityEntity);
        return ActivityMapper.fromActivityEntity(activityEntity);
    }
}
