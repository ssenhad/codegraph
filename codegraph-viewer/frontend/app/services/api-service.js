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
