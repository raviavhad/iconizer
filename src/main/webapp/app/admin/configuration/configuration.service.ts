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
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class JhiConfigurationService {
    constructor(private http: HttpClient) {}

    get(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/configprops', { observe: 'response' }).pipe(
            map((res: HttpResponse<any>) => {
                const properties: any[] = [];
                const propertiesObject = this.getConfigPropertiesObjects(res.body);
                for (const key in propertiesObject) {
                    if (propertiesObject.hasOwnProperty(key)) {
                        properties.push(propertiesObject[key]);
                    }
                }

                return properties.sort((propertyA, propertyB) => {
                    return propertyA.prefix === propertyB.prefix ? 0 : propertyA.prefix < propertyB.prefix ? -1 : 1;
                });
            })
        );
    }

    getConfigPropertiesObjects(res: Object) {
        // This code is for Spring Boot 2
        if (res['contexts'] !== undefined) {
            for (const key in res['contexts']) {
                // If the key is not bootstrap, it will be the ApplicationContext Id
                // For default app, it is baseName
                // For microservice, it is baseName-1
                if (!key.startsWith('bootstrap')) {
                    return res['contexts'][key]['beans'];
                }
            }
        }
        // by default, use the default ApplicationContext Id
        return res['contexts']['iconizer']['beans'];
    }

    getEnv(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/env', { observe: 'response' }).pipe(
            map((res: HttpResponse<any>) => {
                const properties: any = {};
                const propertySources = res.body['propertySources'];

                for (const propertyObject of propertySources) {
                    const name = propertyObject['name'];
                    const detailProperties = propertyObject['properties'];
                    const vals: any[] = [];
                    for (const keyDetail in detailProperties) {
                        if (detailProperties.hasOwnProperty(keyDetail)) {
                            vals.push({ key: keyDetail, val: detailProperties[keyDetail]['value'] });
                        }
                    }
                    properties[name] = vals;
                }
                return properties;
            })
        );
    }
}
