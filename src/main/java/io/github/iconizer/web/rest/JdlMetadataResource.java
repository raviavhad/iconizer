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

package io.github.iconizer.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.iconizer.domain.JdlMetadata;
import io.github.iconizer.security.AuthoritiesConstants;
import io.github.iconizer.service.JdlMetadataService;
import io.github.iconizer.service.UserService;
import io.github.iconizer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JdlMetadata.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.USER)
public class JdlMetadataResource {

    private final Logger log = LoggerFactory.getLogger(JdlMetadataResource.class);

    private static final String ENTITY_NAME = "jdlMetadata";

    private final JdlMetadataService jdlMetadataService;

    private final UserService userService;

    public JdlMetadataResource(JdlMetadataService jdlMetadataService, UserService userService) {
        this.jdlMetadataService = jdlMetadataService;
        this.userService = userService;
    }

    /**
     * PUT  /jdl-metadata : Updates an existing jdlMetadata.
     *
     * @param jdlMetadata the jdlMetadata to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jdlMetadata,
     * or with status 400 (Bad Request) if the jdlMetadata is not valid,
     * or with status 500 (Internal Server Error) if the jdlMetadata couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jdl-metadata")
    @Timed
    public ResponseEntity<JdlMetadata> updateJdlMetadata(@Valid @RequestBody JdlMetadata jdlMetadata) {
        log.debug("REST request to update JdlMetadata : {}", jdlMetadata);
        JdlMetadata result = jdlMetadataService.saveJdlMetadata(jdlMetadata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jdlMetadata.getId()))
            .body(result);
    }

    /**
     * GET  /jdl-metadata : get all the jdlMetadata.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jdlMetadata in body
     */
    @GetMapping("/jdl-metadata")
    @Timed
    public List<JdlMetadata> getAllJdlMetadata(Sort sort) {
        log.debug("REST request to get all JdlMetadata");
        return jdlMetadataService.findAllForUser(userService.getUser(), sort);
    }

    /**
     * GET  /jdl-metadata/:id : get the "id" jdlMetadata.
     *
     * @param id the id of the jdlMetadata to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jdlMetadata, or with status 404 (Not Found)
     */
    @GetMapping("/jdl-metadata/{id}")
    @Timed
    public ResponseEntity<JdlMetadata> getJdlMetadata(@PathVariable String id) {
        log.debug("REST request to get JdlMetadata : {}", id);
        Optional<JdlMetadata> jdlMetadata = jdlMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jdlMetadata.get()));
    }
}
