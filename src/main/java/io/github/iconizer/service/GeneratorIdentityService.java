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

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.iconizer.domain.GeneratorIdentity;
import io.github.iconizer.domain.User;
import io.github.iconizer.repository.GeneratorIdentityRepository;

/**
 * Service Implementation for managing GeneratorIdentity.
 */
@Service
@Transactional
public class GeneratorIdentityService {

    private final Logger log = LoggerFactory.getLogger(GeneratorIdentityService.class);

    private final GeneratorIdentityRepository generatorIdentityRepository;

    public GeneratorIdentityService(GeneratorIdentityRepository generatorIdentityRepository) {
        this.generatorIdentityRepository = generatorIdentityRepository;
    }

    /**
     * Save a generatorIdentity.
     *
     * @param generatorIdentity the entity to save
     * @return the persisted entity
     */
    public GeneratorIdentity save(GeneratorIdentity generatorIdentity) {
        log.debug("Request to save GeneratorIdentity : {}", generatorIdentity);
        return generatorIdentityRepository.save(generatorIdentity);
    }

    /**
     * Get all the generatorIdentities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GeneratorIdentity> findAll() {
        log.debug("Request to get all GeneratorIdentities");
        return generatorIdentityRepository.findAll();
    }

    /**
     * Get all the generatorIdentities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GeneratorIdentity> findAllOwned(User user) {
        log.debug("Request to get all GeneratorIdentities");

        return generatorIdentityRepository.findAllByOwner(user);
    }


    /**
     * Get one generatorIdentity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GeneratorIdentity> findOne(Long id) {
        log.debug("Request to get GeneratorIdentity : {}", id);
        return generatorIdentityRepository.findById(id);
    }

    /**
     * Delete the generatorIdentity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneratorIdentity : {}", id);
        generatorIdentityRepository.deleteById(id);
    }

    /**
     * Try to create a new GeneratorIdentity.
     *
     * @param guid The guid of the new GeneratorIdentity.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 5)
    public void tryToCreateGeneratorIdentity(String guid) {
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityRepository.findFirstByGuidEquals(guid);
        if (!generatorIdentity.isPresent()) {
            // Try to create the GeneratorIdentity in a separate transaction, to manage concurrent access issues
            try {
                save(new GeneratorIdentity().guid(guid));;
            } catch (DataIntegrityViolationException dve) {
                log.info("Could not create GeneratorIdentity {} - it was already created", guid);
            }
        }
    }

    @Transactional
    public Optional<GeneratorIdentity> findOneByGuid(String generatorGuid) {
        return generatorIdentityRepository.findFirstByGuidEquals(generatorGuid);
    }

    @Transactional
    public boolean bindUserToGenerator(User user, String guid) {
        Optional<GeneratorIdentity> generatorIdentity = findOneByGuid(guid);
        if (!generatorIdentity.isPresent()) {
            log.info("GeneratorIdentity {} does not exist", guid);
            return false;
        }
        // Check if the generator has already an owner
        if(generatorIdentity.get().getOwner() != null) {
            return false;
        }
        generatorIdentity.get().owner(user);
        return true;
    }

    @Transactional
    public boolean unbindUserFromGenerator(User user, String guid) {
        Optional<GeneratorIdentity> maybeGeneratorIdentity = generatorIdentityRepository.findFirstByGuidEquals(guid);

        if (!maybeGeneratorIdentity.isPresent()) {
            return false;
        }

        User owner  = maybeGeneratorIdentity.get().getOwner();
        if (owner == null || !owner.equals(user)) {
            return false;
        }

        maybeGeneratorIdentity.get().owner(null);
        return true;
    }
}
