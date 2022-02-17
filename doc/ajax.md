# Ajax

## Native

- use `js/fetch` and handle returned promise. Call to `js->clj` convert JSON to clojurescript data structure (here a map)

```clojure
(-> (js/fetch "https://jsonplaceholder.typicode.com/posts/1")
    (.then #(.json %))
    (.then #(js->clj % :keywordize-keys true))
    (.then #(js/console.log (:title %))))
```

- in the example below, the response body is a JSON array of objects with the `title` property. The code displays the title of the first item.
- *error handling* is also implemented in the first `.then` in the chain. The `status` property of the response is tested, if it is not 200, an exception is thrown. 

```clojure
(-> (js/fetch "https://jsonplaceholder.typicode.com/todos")
    (.then (fn [resp]
            (if (not= 200 (.-status resp))
                (throw "the server did not return HTTP 200")
                (.json resp))))
    (.then #(js/console.log (:title (first (js->clj % :keywordize-keys true)))))
    ;; catch exception thrown by the code (like above) and the ones thrown by network error (unknown host)
    (.catch #(js/console.error "ERROR" %)) 
    (.finally #(js/console.log "cleanup")))
```