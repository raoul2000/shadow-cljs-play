(ns todo.data.sheet-best)

(defn create-todo [m]
  {:id   (.toString (cljs.core/random-uuid))
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

(defn save-todo [td]
  (-> (js/fetch "https://sheet.best/api/sheets/75c11877-0e06-484a-a3d6-301362aca716"
                #js{:method "POST"
                    :mode "cors"
                    :headers #js{"Content-Type" "application/json"}
                    :body (.stringify js/JSON #js{:id   (:id td)
                                                  :text (:text td)
                                                  :done (if (:done td) "TRUE" "FALSE")})})
      (.then #(.json %))))

(defn load-todo-list []
  (-> (js/fetch "https://sheet.best/api/sheets/75c11877-0e06-484a-a3d6-301362aca716")
      (.then handle-json-response)
      (.then normalize)
      (.catch #(js/console.error "ERROR" %))))


(comment
  (load-todo-list)
  (def resp "[{\"id\":\"1\",\"text\":\"task1\",\"done\":\"TRUE\"},{\"id\":\"2\",\"text\":\"buy milk\",\"done\":\"FALSE\"}]")

  (->> (js->clj (.parse js/JSON resp) :keywordize-keys true)
       (map create-todo)
       ;;first
       ;;:text
       (clj->js))

  (-> (js/fetch "https://sheet.best/api/sheets/75c11877-0e06-484a-a3d6-301362aca716"
                (clj->js {:method "POST"
                    :mode "cors"
                    :headers #js{"Content-Type" "application/json"}
                    :body (.stringify js/JSON #js{:id 22233
                                                  :text "new task"
                                                  :done "FALSE"})}))
      (.then #(.json %))
      (.then #(js/console.log %)))
  ()
  (js/fetch "https://sheet.best/api/sheets/75c11877-0e06-484a-a3d6-301362aca716")


  (clj->js {"a" "aaa"})
  (key->js :a)
  ;;
  )