import colors from '../scss/_colors.scss';

class Colors {
    static ALL = Array.from(Object.entries(colors), ([name, value]) => ({ name, value }));
}

export default Colors;
