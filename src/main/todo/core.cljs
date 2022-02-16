(ns todo.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]))

(defn create-todo [text]
  {:id (.toString (cljs.core/random-uuid))
   :text text
   :done false})

(defonce todo-list (r/atom [(create-todo "Hello")]))

(defn clear-todo-list []
  (reset! todo-list []))

(defn add-todo-to-list [text]
  (swap! todo-list conj (create-todo text)))

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
        (add-todo-to-list (.-value input-el))
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

(comment
  (clear-todo-list)
  (add-todo-to-list "111")
  (add-todo-to-list "222")

  (remove-todo-from-list "f1088481-def8-42a5-a68e-8a04feba2fec")
  (mark-done "e91c3d9b-c908-4400-af9f-b0e94c702334" true)
  ;;
  )



