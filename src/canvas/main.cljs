(ns canvas.main
  (:require [canvas :refer [createCanvas] ]
            [cljs.nodejs :as nodejs]))

;; https://codebeautify.org/base64-to-image-converter

(nodejs/enable-util-print!)

(defn msg->img-data-url [^String message]
  (let [canvas (createCanvas 400 400)
        ctx (.. canvas (getContext "2d"))]
    (set! (.. ctx -font)
          "30px Impact")
    (.. ctx (rotate 0.1))
    (.. ctx (fillText message 50 100))
    (.. canvas toDataURL)))

(defn -main [& args]
  (println (msg->img-data-url (first args))))

(set! *main-cli-fn* -main)

(comment
  (sup))
