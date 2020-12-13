(ns day9
  (:require
   [clojure.string :as string]))

(def input-raw (slurp "day9-input.txt"))
(def preamble-size 25)

#_(def input-raw (slurp "day9-example-input.txt"))
#_(def preamble-size 5)

(def input
  (->> input-raw
       string/split-lines
       (map #(Long/parseLong %))))

(defn validate [number previous-numbers]
  (when (every? false?
                (for [x previous-numbers
                      y previous-numbers
                      :when (not= x y)]
                  (= number (+ x y))))
    number))

(def invalid
  (->> (for [window (partition (inc preamble-size) 1 input)]
         (validate
          (last window)
          (drop-last window)))
       (filter some?)
       first))

; ----------------------------------------------------------------------

(for [x (range 2 20)]
  (->> (partition x 1 input)
       (filter #(= invalid (apply + %)))
       (map (fn [arr]
              (+ (apply min arr)
                 (apply max arr))))))
