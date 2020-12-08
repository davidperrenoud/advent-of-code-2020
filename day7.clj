(ns day7
  (:require
   [clojure.string :as string]
   [loom.graph :as loom-graph]
   [loom.io :as loom-io]
   [loom.alg :as loom-alg]))

(def input-raw (slurp "day7-input.txt"))
#_(def input-raw (slurp "day7-example-input.txt"))

(def input-array
  (let [input-clean (string/replace input-raw #"( bags| bag|\.)" "")]
    (for [line (string/split-lines input-clean)]
      (let [[source dests-str] (string/split line #" contain ")
            dests (string/split dests-str #", ")
            dests-no-other (filter #(not= "no other" %) dests)]
        (for [dest dests-no-other]
          (let [[dest-weight-str dest-str] (string/split dest #" " 2)
                dest-weight (Integer/parseInt dest-weight-str)]
            [source dest-str dest-weight]))))))

(def input-reverse-graph
  (->> input-array
       (apply concat)
       (map #(drop-last 1 %))
       (map reverse)
       (apply loom-graph/digraph)))

#_(loom-io/view input-reverse-graph)

(-> (loom-alg/bf-traverse input-reverse-graph "shiny gold")
    count
    dec)

; ----------------------------------------------------------------------

(def input-graph
  (->> input-array
       (apply concat)
       (apply loom-graph/weighted-digraph)))

#_(loom-io/view input-graph)

(defn traverse [node]
  (if-let [successors (loom-graph/successors input-graph node)]
    (->> (for [successor successors]
           (let [weight (loom-graph/weight input-graph node successor)]
             (* weight (traverse successor))))
         (reduce +)
         inc)
    1))

(-> (traverse "shiny gold")
    dec)
