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

- create file `src/main/core.cljs`

```clojure
(ns main.core)

(defn init []
  (println "hello world"))
``` 

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

