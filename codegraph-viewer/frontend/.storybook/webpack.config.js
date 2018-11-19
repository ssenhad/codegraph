const path = require("path");

module.exports = (baseConfig, configType) => {

    baseConfig.module.rules.push(
        {
            test: /\.scss$/,
            use: [{
                loader: "style-loader" // creates style nodes from JS strings
            }, {
                loader: "css-loader" // translates CSS into CommonJS
            }, {
                loader: "sass-loader" // compiles Sass to CSS
            }]
        }
    );
    baseConfig.module.rules.push({ test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'url-loader?limit=10000&mimetype=application/font-woff' });
    baseConfig.module.rules.push({ test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'file-loader' });

    return baseConfig;
};
