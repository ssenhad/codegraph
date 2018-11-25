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
const express = require('express');
const webpack = require('webpack');
const config = require('../webpack.config');

const compiler = webpack(config);

const app = express();

app.use(require('webpack-dev-middleware')(compiler, { noInfo: true, hot: true, inline: true }));
app.use(express.static('../'));

app.use(function (req, res, next) {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Content-Type", "application/json;charset=UTF-8");
    return next();
});

app.get('/api/artifacts/:organization/:artifact/:version/dependencies', function (req, res) {
    const organization = req.params['organization'];
    const artifact = req.params['artifact'];
    const version = req.params['version'];
    res.sendFile(`data/artifacts/dependencies/${organization}_${artifact}_${version}.json`, {root: __dirname});
});

app.get('/api/artifacts/:organization/:artifact/versions', function (req, res) {
    const organization = req.params['organization'];
    const artifact = req.params['artifact'];
    res.sendFile(`data/artifacts/versions/${organization}_${artifact}.json`, {root: __dirname});
});

app.get('/api/artifacts/:organization/:artifact/:version', function (req, res) {
    const organization = req.params['organization'];
    const artifact = req.params['artifact'];
    const version = req.params['version'];
    res.sendFile(`data/artifacts/${organization}_${artifact}_${version}.json`, {root: __dirname});
});

app.get('/api/artifacts/:organization/:artifact/:version/dependency-graph', function (req, res) {
    const organization = req.params['organization'];
    const artifact = req.params['artifact'];
    const version = req.params['version'];
    res.sendFile(`data/artifacts/dependency-graph/${organization}_${artifact}_${version}.json`, {root: __dirname});
});

app.get('/ui/tree/nodes', function (req, res) {
    const parent = req.query['parent'];

    if (!parent) {
        res.sendFile(`data/tree/_root.json`, {root: __dirname});
        return;
    }

    res.sendFile(`data/tree/${parent}.json`, {root: __dirname});
});

app.listen(3000, function () {
    console.log('Codegraph Dev Server');
});


