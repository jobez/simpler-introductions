(ns tooling
  (:require [quil.core :as q]))



(def blue   [53 108 237])
(def yellow [235 229 20])

(declare exit-on-close)
(declare setup)
(declare draw)


(defn setup
  []
  (q/smooth)
  (q/frame-rate 20)
  (q/text-font (q/create-font "" 28 true))
  (q/background 20))  ; dark screen background

(defn draw
  []
  ;; `stroke` sets the stroke color (not the color of the letters).
  (apply q/stroke yellow)

  ;; Note that the border (the stroke) is centered on the point where
  ;; the shape is anchored.
  (q/stroke-weight 20)

  ;; `fill` sets the fill color (text color, and color inside shapes).
  (apply q/fill blue)

  ;; xy coords specify the top left of the rectangle.
  (q/rect 300  ; x
          100  ; y
          150  ; width
          200) ; height

  ;; xy coords specify the *bottom* left of where the text begins.
  (q/text "Hi there"
          300   ; x
          100)  ; y

  ;; One last dark dot to show exactly where that (300, 100)
  ;; point is.
  (q/stroke 20)        ; Use `stroke` to set color of a point.
  (q/stroke-weight 3)  ; Yes, stroke-weight determines point size.
  (q/point 300 100))

(q/defsketch tooling ;; Define a new sketch named example
  :title tooling
  :settings #(q/smooth 2)             ;; Turn on anti-aliasing
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [800 800])
