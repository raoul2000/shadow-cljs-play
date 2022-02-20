(ns calc.application
  (:require [reagent.dom :as rdom]
            ["primereact/button" :as but]
            ["primereact/splitbutton" :as spbut]
            ["primereact/dropdown" :as dropdown]))

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


    ;;
    ]

   (js/document.getElementById "root")))
