(ns counter)

;; let's create a basic counter
;; we will be updating the dom using native JS API

;; state is implemented as an atom
(def counter (atom 0))


(defn update-counter 
  "upodate the counter in page with value *cnt*"
  [cnt]
  (-> js/document
      (.getElementById "counter")
      (.-innerText)
      (set! cnt)))

(defn render 
  "render the counter widget inside element *parent-element* and for
   the state *state-counter*"
  [parent-element state-counter]
  (let [html "<div>
              <div id=\"counter\"></div>
                 <div id=\"button\">
                    <button id=\"btn-inc\">increment</button>
                    <button id=\"btn-dec\">decrement</button>
                 </div>
              </div>"]
    (set! (.-innerHTML parent-element) html)
    (-> js/document
        (.getElementById "btn-inc")
        (.addEventListener "click" #(swap! state-counter inc)))
    (-> js/document
        (.getElementById "btn-dec")
        (.addEventListener "click" #(swap! state-counter dec)))
    (add-watch state-counter :counter-watcher
               (fn [k atom old-v new-v]
                 (update-counter new-v)))))

(defn render-counter [parent-element-id]
  (print "rendering counter ")
  (render (-> js/document
              (.getElementById parent-element-id))
          counter))