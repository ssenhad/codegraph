/*
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
import axios from 'axios';

class ApiService {
    get(url) {
        return axios.get(url).then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response;
        });
    }

    artifactTree(parent) {
        return this.get(`/ui/tree/nodes?parent=${ parent ? parent : ''}`);
    }

    getArtifactVersions({ organization, name }) {
        return this.get(`/api/artifacts/${organization}/${name}/versions`);
    }

    getArtifact({ organization, name, version }) {
        return this.get(`/api/artifacts/${organization}/${name}/${version}`);
    }
}

export default new ApiService();
