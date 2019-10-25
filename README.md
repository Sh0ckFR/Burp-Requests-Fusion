# Burp Requests Fusion

Burp Requests Fusion is a Burp Extension to add custom HTTP headers from a local server to all HTTP requests used by Burp Suite.

## How to use it

* You need to host the PHP file to a local server ( by default: [http://127.0.0.1:8888/api/](http://127.0.0.1:8888/api/) )
* Or use the python3 script :

```
python3 api.py
```

## Requirements

Gradle: [https://gradle.org/install/](https://gradle.org/install/)

## How to compile it

`gradle fatJar`