/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.iconizer.service;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.iconizer.domain.GeneratorIdentity;
import io.github.iconizer.domain.SubGenEvent;
import io.github.iconizer.domain.SubGenEvent_;
import io.github.iconizer.domain.enums.SubGenEventType;
import io.github.iconizer.repository.SubGenEventRepository;
import io.github.iconizer.service.dto.RawSQLField;
import io.github.iconizer.service.dto.TemporalCountDTO;
import io.github.iconizer.service.dto.TemporalDistributionDTO;
import io.github.iconizer.service.enums.TemporalValueType;
import io.github.iconizer.service.util.QueryUtil;

/**
 * Service Implementation for managing SubGenEvent.
 */
@Service
@Transactional
public class SubGenEventService {

    private final Logger log = LoggerFactory.getLogger(SubGenEventService.class);

    private final SubGenEventRepository subGenEventRepository;

    private final EntityManager entityManager;

    public SubGenEventService(SubGenEventRepository subGenEventRepository, EntityManager entityManager) {
        this.subGenEventRepository = subGenEventRepository;
        this.entityManager = entityManager;
    }

    /**
     * Save a subGenEvent.
     *
     * @param subGenEvent the entity to save
     * @return the persisted entity
     */
    public SubGenEvent save(SubGenEvent subGenEvent) {
        log.debug("Request to save SubGenEvent : {}", subGenEvent);
        return subGenEventRepository.save(subGenEvent);
    }

    /**
     * Get all the subGenEvents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SubGenEvent> findAll() {
        log.debug("Request to get all SubGenEvents");
        return subGenEventRepository.findAll();
    }


    /**
     * Get one subGenEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SubGenEvent> findOne(Long id) {
        log.debug("Request to get SubGenEvent : {}", id);
        return subGenEventRepository.findById(id);
    }

    /**
     * Delete the subGenEvent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubGenEvent : {}", id);
        subGenEventRepository.deleteById(id);
    }

    public void deleteByOwner(GeneratorIdentity owner) {
        log.debug("Request to delete SubGenEvent by owner : {}", owner);
        subGenEventRepository.deleteAllByOwner(owner);
    }

    public List<TemporalCountDTO> getFieldCount(Instant after, SubGenEventType field, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQLField> query = builder.createQuery(RawSQLField.class);
        Root<SubGenEvent> root = query.from(SubGenEvent.class);
        ParameterExpression<Instant> dateParameter = builder.parameter(Instant.class, QueryUtil.DATE);
        ParameterExpression<String> typeParameter = builder.parameter(String.class, QueryUtil.TYPE);

        query.select(builder.construct(RawSQLField.class, root.get(dbTemporalFunction.getFieldName()), root.get(SubGenEvent_.type), builder.count(root)))
            .where(builder.greaterThan(root.get(SubGenEvent_.date).as(Instant.class), dateParameter), builder.equal(root.get(SubGenEvent_.type), typeParameter))
            .groupBy(root.get(SubGenEvent_.type), root.get(dbTemporalFunction.getFieldName()));

        return entityManager
            .createQuery(query)
            .setParameter(QueryUtil.DATE, after)
            .setParameter(QueryUtil.TYPE, field.getDatabaseValue())
            .getResultList()
            .stream()
            .map(entry ->
                new TemporalCountDTO(TemporalValueType.absoluteMomentToInstant(entry.getMoment().longValue(), dbTemporalFunction), entry.getCount())
            ).collect(Collectors.toList());
    }

    public List<TemporalDistributionDTO> getDeploymentToolDistribution(Instant after, TemporalValueType dbTemporalFunction) {
        Map<SubGenEventType, List<TemporalCountDTO>> entries = Arrays.stream(SubGenEventType.getDeploymentTools())
            .map(deploymentTool ->
                new AbstractMap.SimpleEntry<>(deploymentTool, getFieldCount(after, deploymentTool, dbTemporalFunction))
            ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        List<TemporalDistributionDTO> result = new ArrayList<>();
        for (Map.Entry<SubGenEventType, List<TemporalCountDTO>> entry: entries.entrySet()) {
            for (TemporalCountDTO count: entry.getValue()) {
                Optional<TemporalDistributionDTO> maybeDistribution = result.stream()
                    .filter(td -> td.getDate().equals(count.getDate()))
                    .findFirst();
                TemporalDistributionDTO distributionDTO;
                if (maybeDistribution.isPresent()) {
                    distributionDTO = maybeDistribution.get();
                } else {
                    distributionDTO = new TemporalDistributionDTO(count.getDate());
                    result.add(distributionDTO);
                }

                distributionDTO.getValues().put(entry.getKey().getDatabaseValue(), count.getCount());
            }
        }

        return result;
    }
}
