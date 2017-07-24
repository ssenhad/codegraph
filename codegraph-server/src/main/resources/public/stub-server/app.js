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


