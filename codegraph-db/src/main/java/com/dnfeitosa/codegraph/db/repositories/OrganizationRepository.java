/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dnfeitosa.codegraph.db.repositories;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@Repository
public class OrganizationRepository {

    private Provider<Session> session;

    @Autowired
    public OrganizationRepository(Provider<Session> session) {
        this.session = session;
    }

    public Set<String> getAllOrganizations() {
        String query = " MATCH (x:Artifact) RETURN x.organization AS org ";
        Result result = getSession().query(query, new HashMap<>());
        return stream(result.spliterator(), false)
            .map(r -> toString(r))
            .collect(toSet());
    }

    private Session getSession() {
        return session.get();
    }

    private String toString(Map<String, Object> r) {
        Object org = r.get("org");
        return org != null ? org.toString() : "";
    }

    public Set<String> getOrganizations(String parent) {
        String query = " MATCH (x:Artifact) WHERE x.organization STARTS WITH {parent} RETURN x.organization AS org ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parent", parent + ".");
        Result result = getSession().query(query, parameters);
        return stream(result.spliterator(), false)
            .map(r -> toString(r))
            .collect(toSet());
    }
}
