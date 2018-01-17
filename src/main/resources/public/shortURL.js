//var http = require('http');
//
//http.createServer(function (request,response) {
//    //发送HTTP头部
//    //HTTP状态值：200：ok
//    //内容类型：text/plain
//    response.writeHead(200,{'Content-Type':'text/plain'});
//    
//    //发送响应数据"Hello World"
//    response.end('Hello World!');
//}).listen(8888);
//
////终端打印如下信息
//console.log('Server running at http://127.0.0.1:8888/');


var express = require('express');
var app = express();
var bodyParser = require('body-parser');
 
// 创建 application/x-www-form-urlencoded 编码解析
var urlencodedParser = bodyParser.urlencoded({ extended: false })
 
app.use(express.static('public'));
 
app.get('/shortURL.htmL', function (req, res) {
   res.sendFile( __dirname + "/" + "shortURL.htmL" );
})
 
app.post('/process_post', urlencodedParser, function (req, res) {
 
   // 输出 JSON 格式
   var response = {
       "url":req.body.url,
   };
   console.log(response);
   res.end(JSON.stringify(response));
})
 
var server = app.listen(8081, function () {
 
  var host = server.address().address
  var port = server.address().port
 
  console.log("访问地址为 http://%s:%s", host, port)
 
})