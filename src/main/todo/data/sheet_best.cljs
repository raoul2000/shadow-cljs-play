(ns todo.data.sheet-best)

(def sheet-best "https://sheet.best/api/sheets/75c11877-0e06-484a-a3d6-301362aca716")

(defn create-todo [m]
  {:id   (:id m)
   :text (:text m)
   :done (= "TRUE" (:done m))})

(defn normalize
  "convert JSON data into list of clojure todo map"
  [json]
  (->> (js->clj json :keywordize-keys true)
       (map create-todo)))

(defn handle-json-response [resp]
  (when-not (.-ok resp)
    (throw (js/Error. "Could not fetch /data")))
  (.json resp))

(defn save-todo [todo]
  (-> (js/fetch sheet-best
                #js{:method  "POST"
                    :mode    "cors"
                    :headers #js{"Content-Type" "application/json"}
                    :body    (.stringify js/JSON #js{:id   (:id todo)
                                                     :text (:text todo)
                                                     :done (if (:done todo) "TRUE" "FALSE")})})
      (.then handle-json-response)))

(defn update-todo [todo]
  (-> (js/fetch (str sheet-best "/id/" (:id todo))
                #js{:method  "PUT"
                    :mode    "cors"
                    :headers #js{"Content-Type" "application/json"}
                    :body    (.stringify js/JSON #js{:id   (:id todo)
                                                     :text (:text todo)
                                                     :done (if (:done todo) "TRUE" "FALSE")})})
      (.then handle-json-response)))

(defn delete-todo [todo-id]
  (-> (js/fetch (str sheet-best "/id/" todo-id)
                #js{:method  "DELETE"})
      (.then handle-json-response)))


(defn load-todo-list []
  (-> (js/fetch sheet-best)
      (.then handle-json-response)
      (.then normalize)
      (.catch #(js/console.error "ERROR" %))))
