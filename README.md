# Shadow-cljs Playground

A playground for *shadow-cljs* experiment and learning ...

## Requirements

- Clojure
- nodejs

## Create Empty Project

```bash
$ npx create-cljs-project shadow-cljs-play
# output skipped ...
$ cd shadow-cljs-play
# show help
$ npx shadow-cljs help
```

## Start REPL

```bash
$ npx shadow-cljs browser-repl
```
- http://localhost:9630/repl-js/browser-repl : browser REPL
- http://localhost:9630 : shadow-cljs server. 

(default port is *9630*)

## Create Entry Point

- create clojurescript code in `./src/main/core.cljs`

```clojure
(ns core)

(defn init []
  (println "hello world"))
``` 

The `init` function is dedicated to be invoked on initial load. This is configured in the *build* configuration file (see [Create a Build](#create-a-build) ).

## Create wrapper HTML page

- create and HTML page in `./public/index.html`

```html
<!DOCTYPE html>
<html>
  <head>
      <title>App</title>
  </head>
  <body>
      <script src="/js/main.js"></script>
  </body>
</html>
```

The script `/js/main.js` is produced by the build process. 

## Create a Build

- create a build target called *frontend* with entry point the `core/init` function.
- configure an HTTP server with root `./public` folder and port *8080*

`shadow-cljs.edn`
```clojure
{:source-paths
 ["src/dev"
  "src/main"
  "src/test"]

 :dependencies
 []

 :dev-http {8080 "public"}

 :builds
 {:frontend {:target :browser
             :modules {:main {:init-fn core/init}}}}}
```

