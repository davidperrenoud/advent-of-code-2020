(ns day2
  (:require
   [clojure.string :as string]))

(def input-raw (string/split-lines (slurp "day2-input.txt")))

(defn split-line [line]
  (let [[_ a b c d] (re-matches #"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)" line)]
    {:first-num (Integer/parseInt a)
     :second-num (Integer/parseInt b)
     :letter (first c)
     :password d}))

(def input (map split-line input-raw))

; Sled rental policy
(defn validate [{:keys [first-num second-num letter password]}]
  (let [freq (frequencies password)
        letter-freq (get freq letter 0)]
    (<= first-num letter-freq second-num)))

; Toboggan corporate policy
#_(defn validate [{:keys [first-num second-num letter password]}]
  (let [first-char (get password (dec first-num))
        second-char (get password (dec second-num))]
    (not= (= letter first-char)
          (= letter second-char))))

#_(validate {:first-num 1 :second-num 3 :letter \a :password "abcde"})
#_(validate {:first-num 1 :second-num 3 :letter \b :password "cdefg"})
#_(validate {:first-num 2 :second-num 9 :letter \c :password "ccccccccc"})

(->> input
     (map validate)
     (filter true?)
     count)
