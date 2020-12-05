(ns day3
  (:require
   [clojure.string :as string]))

(def input-raw (slurp "day3-input.txt"))

(def input
  (->> (string/split input-raw #"\n")
       (mapv (fn [line]
               (mapv #(= % \#) line)))))

; From left-top to right-bottom
(defn get-recurring-tree [x y]
  (let [line-length (count (first input))]
    (-> input
        (get y)
        (get (mod x line-length)))))

#_(get-recurring-tree 0 0)
#_(get-recurring-tree 0 1)
#_(get-recurring-tree 1 0)

(defn count-diagonal [offset-x offset-y]
  (->> (for [y (range (count input))]
         (get-recurring-tree (* offset-x y) (* offset-y y)))
       (filter true?)
       count))

(count-diagonal 3 1)

; ----------------------------------------------------------------------

(* (count-diagonal 1 1)
   (count-diagonal 3 1)
   (count-diagonal 5 1)
   (count-diagonal 7 1)
   (count-diagonal 1 2))
