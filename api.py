#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'''
Python3 HTTP server.
'''

import time
import json
from http.server import BaseHTTPRequestHandler, HTTPServer

HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8888

HEADERS = dict()
HEADERS['FIRST_HTTP_HEADER'] = 'foo'
HEADERS['SECOND_HTTP_HEADER'] = 'bar'

class HTTPHandler(BaseHTTPRequestHandler):
    def do_HEAD(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_GET(self):
        paths = {
            '/api/': {'status': 200}
        }

        if self.path in paths:
            self.respond(paths[self.path])
        else:
            self.respond({'status': 500})

    def log_message(self, format, *args):
        '''This function is used to disable logging'''
        return

    def handle_http(self, status_code):
        self.send_response(status_code)
        self.send_header('Content-type', 'application/json')
        self.end_headers()
        content = ''
        if status_code == 200:
            content = json.dumps(HEADERS)
        return bytes(content, 'UTF-8')

    def respond(self, opts):
        response = self.handle_http(opts['status'])
        self.wfile.write(response)

if __name__ == '__main__':
    SERVER = HTTPServer
    HTTPD = SERVER((HOST_NAME, PORT_NUMBER), HTTPHandler)
    print(time.asctime(), 'Server Starts - %s:%s' % (HOST_NAME, PORT_NUMBER))
    try:
        HTTPD.serve_forever()
    except KeyboardInterrupt:
        pass
    HTTPD.server_close()
    print(time.asctime(), 'Server Stops - %s:%s' % (HOST_NAME, PORT_NUMBER))
