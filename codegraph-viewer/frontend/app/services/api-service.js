import axios from 'axios';

class ApiService {
    get(url) {
        return axios.get(url).then((response) => {
            return response.data;
        });
    }

    artifactTree(parent) {
        return this.get(`/ui/tree/nodes?parent=${ parent ? parent : ''}`);
    }
}

export default new ApiService();
