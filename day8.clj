(ns day8
  (:require
   [clojure.string :as string]))

(def input-raw (slurp "day8-input.txt"))
#_(def input-raw "nop +0\nacc +1\njmp +4\nacc +3\njmp -3\nacc -99\nacc +1\njmp -4\nacc +6")

(def input
  (for [line (string/split-lines input-raw)]
    (let [[operation number] (string/split line #" ")]
      {:operation (keyword operation)
       :number (Integer/parseInt number)})))

(defn traverse [input {:keys [index accumulator visited]}]
  (let [inputv (into [] input)
       {:keys [operation number]} (get inputv index)]
    #_(println index accumulator visited)
    (if (= index (count input))
      {:end accumulator}
      (if (get visited index)
        {:loop accumulator}
        (case operation
          :acc (traverse input {:index (inc index)
                                :accumulator (+ accumulator number)
                                :visited (conj visited index)})
          :jmp (traverse input {:index (+ index number)
                                :accumulator accumulator
                                :visited (conj visited index)})
          :nop (traverse input {:index (inc index)
                                :accumulator accumulator
                                :visited (conj visited index)}))))))

#_(traverse input {:index 0
                   :accumulator 0
                   :visited #{}})

; ----------------------------------------------------------------------

(defn rand-modified-input []
  (let [rand-index (rand-int (count input))
        inputv (into [] input)
        line (get inputv rand-index)
        operation (:operation line)]
    (case operation
      :acc inputv
      :jmp (assoc inputv rand-index (assoc line :operation :nop))
      :nop (assoc inputv rand-index (assoc line :operation :jmp)))))

#_(->> (repeatedly #(traverse (rand-modified-input) {:index 0
                                                     :accumulator 0
                                                     :visited #{}}))
       (take 100)
       (filter :end))
