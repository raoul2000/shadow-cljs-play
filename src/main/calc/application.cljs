(ns calc.application
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            ["primereact/button" :as but]
            ["primereact/splitbutton" :as spbut]
            ["primereact/dropdown" :as dropdown]
            ["primereact/autocomplete" :as autoc]))

(def state (r/atom {:all-countries      ["paris" "londres" "madrid" "marseille"]
                    :filtered-countries []
                    :selected-country   ""}))

(defn on-change [ev]
  (let [value (.-value ev)]
    (js/console.log "onChange : " value)
    (swap! state (fn [o]
                   (update o :selected-country (fn [prev] 
                                                 (str prev value)))))
    ))

(defn complete-method [ev]
  (let [q (.-query ev)]
    (js/console.log "complete: " q (count q))
    (swap! state (fn [o]
                   (prn (:all-countries o))
                   (assoc o :filtered-countries
                          (if (= 0 (count q))
                            (:all-countries o)
                            (filter #(.startsWith % q) (:all-countries o)))
                          )))))


(defn my-auto-complete []
  (let [{:keys [filtered-countries selected-country]} @state]
    
        [:> autoc/AutoComplete {:value           selected-country
                                :suggestions     filtered-countries
                                :dropdown        true
                                :completeMethod  complete-method
                                :onChange        on-change}]
    ))

(defn render []
  (rdom/render
   ;;[:h1 "hello"]
   [:div
    [:h1 "Using Primereact Components"]
    [:h2 "Buttons"]
    [:div {:class "card"}
    ;;https://www.primefaces.org/primereact/button/
     [:> but/Button {:label   "Save !"
                     :iconPos "right"
                     :loading true}]

     [:> but/Button {:label   "Save"
                     :iconPos "left"
                     :icon    "pi pi-check"}]

     ;; https://www.primefaces.org/primereact/splitbutton/
     [:> spbut/SplitButton {:label "Splitted"
                            :className "mr-2 mb-2"
                            :model [{:label "upload"
                                     :icon "pi pi-upload"
                                     :command #(js/console.log "uploading")}

                                    {:label "React website"
                                     :icon  "pi pi-external-link"
                                     :command #(set! (.-href js/window.location) "https://facebook.github.io/react/")}]}]

     ;;
     ]
    [:h2 "Dropdown"]
    [:> dropdown/Dropdown {:placeholder "select something"
                           :optionLabel "name"
                           :options [{:name "paris" :code "p"}
                                     {:name "london" :code "lf"}]}]

    [:h2 "Auto Complete"]
    (my-auto-complete)

    ;;
    ]

   (js/document.getElementById "root")))
