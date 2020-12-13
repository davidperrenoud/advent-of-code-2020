(ns day10)

(def input-raw (slurp "day10-input.txt"))
#_(def input-raw (slurp "day10-example1-input.txt"))
#_(def input-raw (slurp "day10-example2-input.txt"))

(def input (read-string (str "[" input-raw "]")))

(def max-rating (+ 3 (apply max input)))

(def diffs
  (->> (concat [0] input [max-rating])
       sort
       (partition 2 1)
       (map (fn [[x y]] (- y x)))))

(frequencies diffs)

; ----------------------------------------------------------------------

(def freqs
  (->> diffs
       (partition-by identity)
       frequencies))

(get freqs [1 1 1 1]) ; = 7
(get freqs [1 1 1]) ; = 4
(get freqs [1 1]) ; = 2

; => 7^12*4^5*2^2
