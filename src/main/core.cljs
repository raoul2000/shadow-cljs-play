(ns core
  (:require [counter :as counter]
            [todo.core :as todo]
            [calc.application :as calc]
            
            ))

(defn init []
  (js/console.clear)
  (println "hello world")
  ;;(counter/render-counter "root")
  ;;(todo/application)
  (calc/render)
  ;;
  )

(defn start []
  (js/console.log "start")
  (init))

(comment
  ;; on reload lifecycle hooks can be set in the configuration (shadow-cljs.edn)
  ;; or like below, directly as function metadata
  (defn ^:dev/before-load before-reload []
    (js/console.log "stop"))


  (defn ^:dev/after-load after-reload []
    (js/console.clear)
    (init)))