/*
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
var express = require('express');
var fs = require('fs');
var path = require('path');

var app = express();

app.use(express.static('../'));

app.use(function (req, res, next) {
    res.setHeader("Access-Control-Allow-Origin", "*");
    return next();
});

app.get('/api/artifacts', function (req, res) {
    res.sendFile("data/artifacts.json", { root: __dirname });
});

app.get('/api/artifacts/:organization/:artifact/:version/dependencies', function (req, res) {
    var organization = req.params['organization'];
    var artifact = req.params['artifact'];
    var version = req.params['version'];
    res.sendFile(`data/artifacts/dependencies/${organization}_${artifact}_${version}.json`, { root: __dirname })
});

app.get('/api/artifacts/:organization/:artifact/versions', function (req, res) {
    var organization = req.params['organization'];
    var artifact = req.params['artifact'];
    res.sendFile(`data/artifacts/versions/${organization}_${artifact}.json`, { root: __dirname })
});

app.get('/api/artifacts/:organization/:artifact/:version', function (req, res) {
    var organization = req.params['organization'];
    var artifact = req.params['artifact'];
    var version = req.params['version'];
    res.sendFile(`data/artifacts/${organization}_${artifact}_${version}.json`, { root: __dirname })
});

app.get('/api/artifacts/:organization/:artifact/:version/dependency-graph', function (req, res) {
    var organization = req.params['organization'];
    var artifact = req.params['artifact'];
    var version = req.params['version'];
    res.sendFile(`data/artifacts/dependency-graph/${organization}_${artifact}_${version}.json`, { root: __dirname })
});

app.listen(3000, function () {
    console.log('Codegraph Server Stub');
});


