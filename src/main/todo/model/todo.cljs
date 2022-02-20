(ns todo.model.todo
  (:require [reagent.core :as r]))

(defonce todo-list (r/atom '()))

(defn create-todo
  ([text completed]
   {:id (.toString (cljs.core/random-uuid))
    :text text
    :done completed})
  ([text]
   (create-todo text false)))

(defn clear-todo-list []
  (reset! todo-list '()))

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
                     (map #(if (= todo-id (:id %))
                             (assoc % :done done?)
                             %)
                          ov))))
(defn get-todo-list [] 
  @todo-list)