(ns day5
  (:require
   [clojure.string :as string]))

(def input (string/split-lines (slurp "day5-input.txt")))

(defn to-seat-id [input-str]
  (let [row (-> input-str
                (subs 0 7)
                (string/replace #"F" "0")
                (string/replace #"B" "1")
                (Integer/parseInt 2))
        region (-> input-str
                   (subs 7)
                   (string/replace #"L" "0")
                   (string/replace #"R" "1")
                   (Integer/parseInt 2))]
    (-> row (* 8) (+ region))))

#_(to-seat-id "FBFBBFFRLR")

(->> input
     (map to-seat-id)
     (apply max))

; ----------------------------------------------------------------------

(def input-set
  (->> input
       (map to-seat-id)
       (set)))

(for [row (range 1 127)
      region (range 0 8)
      :let [seat-id (-> row (* 8) (+ region))]
      :when (and (contains? input-set (dec seat-id))
                 (not (contains? input-set seat-id))
                 (contains? input-set (inc seat-id)))]
  seat-id)
