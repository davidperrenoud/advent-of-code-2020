(ns day6
  (:require
   [clojure.string :as string]
   [clojure.set :as set]))

(def input-raw (slurp "day6-input.txt"))

(def answer-sets
  (->> (string/split input-raw #"\n\n")
       (map string/split-lines)
       (map #(map set %))))

(->> answer-sets
     (map #(apply set/union %))
     (map count)
     (reduce +))

; ----------------------------------------------------------------------

(->> answer-sets
     (map #(apply set/intersection %))
     (map count)
     (reduce +))
