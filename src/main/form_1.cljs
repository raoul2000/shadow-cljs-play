(ns main.form-1
  (:require [goog.dom :as dom]
            [goog.string :as str]
            [goog.events :as events]
            [goog.dom.forms :as forms]))

;; manipulate the DOM, Events a,d Forms via google closure library

(defn submit-handler [ev input-element message-element]
  (events/Event.preventDefault ev)
  (let [input-name (forms/getValue input-element)]
    (js/console.log input-name)
    (forms/setValue input-element "")
    (dom/setTextContent message-element (str "you entered " input-name))))

(def html (str/Const.from
           "<div>
            <form>
             <label>your name:</label>
             <input id=\"input-name\" type=\"text\" autocomplete=\"off\"/>
             <button id=\"btn-submit-name\">Save</button>
           </form>
            <div id=\"info-message\"></div>
            </div>"))

(defn render [parent-element]
  (->> (dom/constHtmlToNode html)
       (dom/append parent-element))
  (events/listen (dom/getElement "btn-submit-name")
                 "click"
                 #(submit-handler % 
                                  (dom/getElement "input-name")
                                  (dom/getElement "info-message"))))


(defn render-form-1 [parent-element-id]
  (render (-> js/document
              (.getElementById parent-element-id))))

(comment
  (dom/removeChildren (dom/getElement "root"))
  (render-form-1 "root")
  ;;
)