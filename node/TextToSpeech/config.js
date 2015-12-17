
'use strict';
var express    = require('express'),
  bodyParser   = require('body-parser');

module.exports = function (app) {
	app.use(express.static(__dirname + '/client'));
  app.engine('html', require('ejs').renderFile);
  app.set('view engine', 'html');
  app.set('views', __dirname + '/client');
  app.use(bodyParser.urlencoded({ extended: true, limit: '5mb' }));
  app.use(bodyParser.json({ limit: '5mb' }));
  
};
