# Hot Reload

## Set reload hooks

Lifecycle hooks can be set as function metadata to trigger function call on reload. 

`./src/main/core.cljs`
```clojure
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(defn ^:dev/after-load start []
  (js/console.clear)
  (init))
```

Another option is to configured a hook in the configuration file. For example :

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
             :modules {:main {:init-fn core/init}}
             ;; configure lifecycle hook
             ;; after reload call function core/start
             :devtools {:after-load   core/start}}}
 }
```


## Reference
- [Lifecycle Hooks](https://shadow-cljs.github.io/docs/UsersGuide.html#_lifecycle_hooks)
