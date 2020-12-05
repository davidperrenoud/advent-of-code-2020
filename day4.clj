(ns day4
  (:require
   [clojure.string :as string]))

(def input-raw (slurp "day4-input.txt"))

(def input
  (for [line (string/split input-raw #"\n\n")]
    (->> (for [attr (string/split line #"\s+")]
           (let [[key value] (string/split attr #":")
                 kw (keyword key)]
             [kw value]))
         (reduce (fn [acc [kw value]]
                   (assoc acc kw value))
                 {}))))

(->> input
     (filter #(and (:byr %)
                   (:iyr %)
                   (:eyr %)
                   (:hgt %)
                   (:hcl %)
                   (:ecl %)
                   (:pid %)))
     count)

; ----------------------------------------------------------------------

(defn parse-int-else-0 [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception _ 0)))

(defn validate-byr [byr] (<= 1920 (parse-int-else-0 byr) 2002))
(defn validate-iyr [iyr] (<= 2010 (parse-int-else-0 iyr) 2020))
(defn validate-eyr [eyr] (<= 2020 (parse-int-else-0 eyr) 2030))

(defn validate-hgt [hgt]
  (let [length (count hgt)]
    (when (>= length 3)
      (let [last-two-chars (subs hgt (- length 2) length)
            first-chars (subs hgt 0 (- length 2))
            number (parse-int-else-0 first-chars)]
        (case last-two-chars
          "cm" (<= 150 number 193)
          "in" (<= 59 number 76)
          nil)))))

(defn validate-hcl [hcl] (when hcl (re-matches #"#[0-9a-f]{6}" hcl)))
(defn validate-ecl [ecl] (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))
(defn validate-pid [pid] (when pid (re-matches #"[0-9]{9}" pid)))

#_(validate-byr (second input))
#_(validate-hgt (first input))

(->> input
     (filter #(and (validate-byr (:byr %))
                   (validate-iyr (:iyr %))
                   (validate-eyr (:eyr %))
                   (validate-eyr (:eyr %))
                   (validate-hgt (:hgt %))
                   (validate-hcl (:hcl %))
                   (validate-ecl (:ecl %))
                   (validate-pid (:pid %))))
     count)
