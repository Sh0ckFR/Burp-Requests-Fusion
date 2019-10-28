# Burp Requests Fusion

Burp Requests Fusion is a Burp Extension to add custom HTTP headers (dynamic headers also) from a local server to all HTTP requests used by Burp Suite.

## How to use it

### With Python3

* Edit api.py to add your custom headers
* Start the server: `python3 api.py`

### With PHP
* You need to host the PHP file to a local server ( by default: [http://127.0.0.1:8888/api/](http://127.0.0.1:8888/api/) )
* Define your headers in this PHP file (dynamic or not)
* Enable the extension on Burp
* Enjoy

## Requirements

Gradle: [https://gradle.org/install/](https://gradle.org/install/)

## How to compile it

`gradle fatJar`
