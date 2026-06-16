package dev.cost.management.estimatemanagementapi.domain.models.estimate.events;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import project.az.application.publisher.domain.events.DomainEvent;

public record IssuedEstimate(Estimate estimate) implements DomainEvent {

}
