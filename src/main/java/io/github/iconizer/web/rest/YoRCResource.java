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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import io.github.iconizer.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import io.github.iconizer.domain.YoRC;
import io.github.iconizer.service.YoRCService;
import io.github.iconizer.web.rest.errors.BadRequestAlertException;
import io.github.iconizer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing YoRC.
 */
@RestController
@RequestMapping("/api")
public class YoRCResource {

    private final Logger log = LoggerFactory.getLogger(YoRCResource.class);

    private static final String ENTITY_NAME = "yoRC";

    private final YoRCService yoRCService;

    public YoRCResource(YoRCService yoRCService) {
        this.yoRCService = yoRCService;
    }

    /**
     * POST  /yo-rcs : Create a new yoRC.
     *
     * @param yoRC the yoRC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yoRC, or with status 400 (Bad Request) if the yoRC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/yo-rcs")
    @Timed
    public ResponseEntity<YoRC> createYoRC(@RequestBody YoRC yoRC) throws URISyntaxException {
        log.debug("REST request to save YoRC : {}", yoRC);
        if (yoRC.getId() != null) {
            throw new BadRequestAlertException("A new yoRC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YoRC result = yoRCService.save(yoRC);
        return ResponseEntity.created(new URI("/api/yo-rcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yo-rcs : Updates an existing yoRC.
     *
     * @param yoRC the yoRC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yoRC,
     * or with status 400 (Bad Request) if the yoRC is not valid,
     * or with status 500 (Internal Server Error) if the yoRC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/yo-rcs")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<YoRC> updateYoRC(@RequestBody YoRC yoRC) {
        log.debug("REST request to update YoRC : {}", yoRC);
        if (yoRC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        YoRC result = yoRCService.save(yoRC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, yoRC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yo-rcs : get all the yoRCS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of yoRCS in body
     */
    @GetMapping("/yo-rcs")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public List<YoRC> getAllYoRCS() {
        log.debug("REST request to get all YoRCS");
        return yoRCService.findAll();
    }

    /**
     * GET  /yo-rcs/:id : get the "id" yoRC.
     *
     * @param id the id of the yoRC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yoRC, or with status 404 (Not Found)
     */
    @GetMapping("/yo-rcs/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<YoRC> getYoRC(@PathVariable Long id) {
        log.debug("REST request to get YoRC : {}", id);
        Optional<YoRC> yoRC = yoRCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yoRC);
    }

    /**
     * DELETE  /yo-rcs/:id : delete the "id" yoRC.
     *
     * @param id the id of the yoRC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/yo-rcs/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<Void> deleteYoRC(@PathVariable Long id) {
        log.debug("REST request to delete YoRC : {}", id);
        yoRCService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
