(ns todo.core
  (:require [reagent.dom :as dom]
            [todo.model.todo :as model]
            [todo.data.json-placehoder :as jp]
            [todo.data.sheet-best :as sheet]))

;; ==========================================================
;; handlers

(defn handle-add-todo []
  (let [input-el   (js/document.getElementById "new-todo")
        todo       (model/create-todo (.-value input-el))]
    (-> (sheet/save-todo todo)
        (.then #(do
                  (set! (.-value input-el) "")
                  (model/add-todo-to-list todo))))))

(defn handle-toggle-done [todo]
  (let [updated-todo (update todo :done not)]
    (-> (sheet/update-todo updated-todo)
        (.then #(model/mark-done (:id updated-todo)  (:done updated-todo))))))

(defn handle-delete-todo [todo-id]
  (when (js/confirm "Are you sure you want to delete this item ? ")
    (-> (sheet/delete-todo todo-id)
        (.then #(model/remove-todo-from-list todo-id)))))

;; ==========================================================
;; components

(defn todo-item-component-mapper [todo]
  [:div.todo {:key (:id todo) :data-id (:id todo)}
   [:div.text {:style {:text-decoration (if (:done todo) "line-through" "none")}}
    (:text todo)]
   [:button
    {:on-click #(handle-toggle-done todo)}
    (if (:done todo) "Undo" "Done")]
   [:button
    {:onClick #(handle-delete-todo (:id todo))}
    "Delete"]])

(defn todo-list-component []
  [:div.todo-list
   (map todo-item-component-mapper (model/get-todo-list))])

(defn todo-form-component []
  [:div#new-form.input
   [:input#new-todo {:type "text"
                     :autoComplete "off"
                     :placeholder "enter todo ..."}]
   [:button
    {:onClick handle-add-todo}
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
  (->  (sheet/load-todo-list) ;;(jp/load-todo-list)
       (.then (fn [todo-v]
                (doseq [todo todo-v]
                  (js/console.log "text" (:text todo))
                  (model/add-todo-to-list  todo))))))

(defn application []
  (-> (fetch-todos)
      (.then render)))
