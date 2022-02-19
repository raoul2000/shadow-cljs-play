(ns todo.data.json-placehoder)

(defn create-todo [m]
  {:id (.toString (cljs.core/random-uuid))
   :text (:title m)
   :done false})

(defn handle-json-response [resp]
  (if (not= 200 (.-status resp))
    (throw "the server did not return HTTP/200")
    (.json resp)))

(defn normalize
  "convert JSON data into list of clojure todo map"
  [json]
  (->> (js->clj json :keywordize-keys true)
       (map create-todo)))

(defn load-todo-list []
  (-> (js/fetch "https://jsonplaceholder.typicode.com/todos")
      (.then handle-json-response)
      (.then normalize)
      (.catch #(js/console.error "ERROR" %))))
