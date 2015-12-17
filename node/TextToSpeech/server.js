
'use strict';

var express    = require('express'),
  app          = express(),
  watson       = require('watson-developer-cloud');
require('./config')(app);
var textToSpeech = watson.text_to_speech({
  version: 'v1',
  username: '0923b621-e11e-455e-aa28-bdc77d5aae41',
  password: 'YfXEkx6VkAzO'
});
app.get('/', function(req, res, next) {
  res.render('textToSpeech');
});
app.post('/convertAudioToText', function(req, res, next) {
    console.log('success');
});
app.get('/convertTextToAudio', function(req, res, next) {
  var transcript = textToSpeech.synthesize(req.query);
  transcript.on('response', function(response) {
    console.log('success');
  });
  transcript.on('error', function(error) {
    next(error);
  });
  transcript.pipe(res);
});
var port = process.env.VCAP_APP_PORT || 8084;
app.listen(port);
console.log('listening at:', port);
