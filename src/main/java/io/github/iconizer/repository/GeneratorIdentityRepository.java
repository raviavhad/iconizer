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

package io.github.iconizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.iconizer.domain.GeneratorIdentity;
import io.github.iconizer.domain.User;

/**
 * Spring Data JPA repository for the GeneratorIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneratorIdentityRepository extends JpaRepository<GeneratorIdentity, Long> {

    Optional<GeneratorIdentity> findFirstByGuidEquals(String guid);

    List<GeneratorIdentity> findAllByOwner(User owner);

    @Query("select generator_identity from GeneratorIdentity generator_identity where generator_identity.owner.login = ?#{principal.username}")
    List<GeneratorIdentity> findByOwnerIsCurrentUser();
}
