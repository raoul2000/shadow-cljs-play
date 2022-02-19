(ns todo.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]
            [todo.data.json-placehoder :as jp]
            [ajax.core :refer [GET POST json-response-format]]))

(defn create-todo
  ([text completed]
   {:id (.toString (cljs.core/random-uuid))
    :text text
    :done completed})
  ([text]
   (create-todo text false)))

(defonce todo-list (r/atom []))

(defn clear-todo-list []
  (reset! todo-list []))

(defn add-todo-to-list [todo]
  (swap! todo-list conj todo))

(defn id-equals? [id]
  (fn [entry]
    (= id (:id entry))))

(defn remove-todo-from-list [id]
  (swap! todo-list #(remove (id-equals? id) %)))

(defn mark-done [todo-id done?]
  (js/console.log todo-id)
  (swap! todo-list (fn [ov]
                     (mapv #(if (= todo-id (:id %))
                              (assoc % :done done?)
                              %)
                           ov))))

;; ==========================================================
;; components

(defn todo-item-component-mapper [todo]
  [:div.todo {:key (:id todo)}
   [:div.text {:style {:text-decoration (if (:done todo) "line-through" "none")}}
    (:text todo)]
   [:button
    {:on-click #(mark-done (:id todo) (not (:done todo)))}
    (if (:done todo) "Undo" "Done")]
   [:button
    {:onClick #(remove-todo-from-list (:id todo))}
    "Delete"]])

(defn todo-list-component []
  [:div.todo-list
   (map todo-item-component-mapper @todo-list)])

(defn todo-form-component []
  [:div#new-form.input
   [:input#new-todo {:type "text"
                     :autoComplete "off"
                     :placeholder "enter todo ..."}]
   [:button
    {:onClick
     #(let [input-el   (js/document.getElementById "new-todo")]
        (add-todo-to-list (create-todo (.-value input-el)))
        (set! (.-value input-el) ""))}
    "Add Todo"]])

(defn todo-app-component []
  [:div.todo-list-container
   [:h1 "Todo list"]
   [todo-list-component]
   [todo-form-component]])

(defn render []
  (dom/render
   [todo-app-component]
   (js/document.getElementById "root")))

(defn fetch-todos []
  (-> (jp/load-todo-list)
      (.then (fn [todo-v]
               (doseq [todo (take 10 todo-v)]
                 (js/console.log "text" (:text todo))
                 (add-todo-to-list  (create-todo (:text todo))))))))

(defn application []
  (-> (fetch-todos)
      (.then render)))

(comment
  (clear-todo-list)

  (remove-todo-from-list "f1088481-def8-42a5-a68e-8a04feba2fec")
  (mark-done "e91c3d9b-c908-4400-af9f-b0e94c702334" true)

  ;;
  )



