const http = require('http');
const fs = require('fs');

http.createServer(function(req, res) {
    fs.readFile('/api/', (err, data) => {
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.write(JSON.stringify({
            'FIRST_HTTP_HEADER': 'foo',
            'SECOND_HTTP_HEADER': 'bar'
        }));
        res.end();
    });
}).listen(8888);
