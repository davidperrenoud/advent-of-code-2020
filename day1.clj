(ns day1
  (:require
   [clojure.set :as set]))

(def input (read-string (str "[" (slurp "day1-input.txt") "]")))

(def goal 2020)

; O(n)
(->> input
     (map #(- goal %))
     (set)
     (clojure.set/intersection (set input))
     (reduce *))

; ----------------------------------------------------------------------

; O(n^3)
(for [input1 input
      input2 input
      input3 input
      :when (< input1 input2 input3)
      :when (= goal (+ input1 input2 input3))]
  (* input1 input2 input3))
