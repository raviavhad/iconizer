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

package io.github.iconizer.service.yorc;

import java.time.Instant;
import java.util.List;

import io.github.iconizer.service.DataGenerationFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.iconizer.IconizerApp;
import io.github.iconizer.repository.YoRCRepository;
import io.github.iconizer.service.YoRCService;
import io.github.iconizer.service.dto.TemporalCountDTO;
import io.github.iconizer.service.enums.TemporalValueType;
import org.springframework.transaction.annotation.Transactional;

import static java.time.ZonedDateTime.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = IconizerApp.class)
public class YoRCServiceCountsIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    @Transactional
    @Test
    public void assertThatYearCountIsCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalCountDTO> result = yoRCService.getCount(
            parse("2018-01-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            TemporalValueType.YEAR
        );

        assertThat(result)
            .hasSize(2)
            .extracting(TemporalCountDTO::getDate, TemporalCountDTO::getCount)
            .contains(
                tuple(Instant.parse("2018-01-01T00:00:00Z"), 8L),
                tuple(Instant.parse("2019-01-01T00:00:00Z"), 4L)
            );
    }

    @Transactional
    @Test
    public void assertThatMonthCountIsCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalCountDTO> result = yoRCService.getCount(
            parse("2018-01-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            TemporalValueType.MONTH
        );

        assertThat(result)
            .hasSize(7)
            .extracting(TemporalCountDTO::getDate, TemporalCountDTO::getCount)
            .contains(
                tuple(Instant.parse("2018-02-01T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-03-01T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-04-01T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-05-01T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-06-01T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-09-01T00:00:00Z"), 3L),
                tuple(Instant.parse("2019-01-01T00:00:00Z"), 4L)
            );
    }

    @Transactional
    @Test
    public void assertThatWeekCountIsCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalCountDTO> result = yoRCService.getCount(
            parse("2018-06-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            TemporalValueType.WEEK
        );

        assertThat(result)
            .hasSize(3)
            .extracting(TemporalCountDTO::getDate, TemporalCountDTO::getCount)
            .contains(
                tuple(Instant.parse("2018-12-27T00:00:00Z"), 4L),
                tuple(Instant.parse("2018-09-13T00:00:00Z"), 2L),
                tuple(Instant.parse("2018-09-27T00:00:00Z"), 1L)
            );
    }

    @Transactional
    @Test
    public void assertThatDayCountIsCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalCountDTO> result = yoRCService.getCount(
            parse("2018-06-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            TemporalValueType.DAY
        );

        assertThat(result)
            .hasSize(5)
            .extracting(TemporalCountDTO::getDate, TemporalCountDTO::getCount)
            .contains(
                tuple(Instant.parse("2018-09-27T00:00:00Z"), 1L),
                tuple(Instant.parse("2019-01-01T00:00:00Z"), 2L),
                tuple(Instant.parse("2019-01-02T00:00:00Z"), 2L),
                tuple(Instant.parse("2018-09-13T00:00:00Z"), 1L),
                tuple(Instant.parse("2018-09-14T00:00:00Z"), 1L)
            );
    }

    @Transactional
    @Test
    public void assertThatHourCountIsCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalCountDTO> result = yoRCService.getCount(
            parse("2019-01-01T01:01:01.000+01:00[Europe/Paris]").toInstant(),
            TemporalValueType.HOUR
        );

        assertThat(result)
            .hasSize(3)
            .extracting(TemporalCountDTO::getDate, TemporalCountDTO::getCount)
            .contains(
                tuple(Instant.parse("2019-01-01T13:00:00Z"), 1L),
                tuple(Instant.parse("2019-01-02T16:00:00Z"), 2L),
                tuple(Instant.parse("2019-01-01T05:00:00Z"), 1L)
            );
    }
}
