(ns my-first-clojure-project.hobbit-game)
(:require [my-first-clojure-project.core :as core-class])

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

;(defn symmetrize-body-parts
;  "Expects a seq of maps that have a :name and :size"
;  [asym-body-parts]
;  (loop [remaining-asym-parts asym-body-parts
;         final-body-parts []]
;    (if (empty? remaining-asym-parts)
;      final-body-parts
;      (let [[part & remaining] remaining-asym-parts]
;        (recur remaining
;               (into final-body-parts
;                     (set [part (matching-part part)])))))))

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts ; `asym-body-parts` is a starting value for `remaining-asym-parts`
         final-body-parts []] ;`[]` is a starting value for  `final-body-parts`
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts] ; deconstruction
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)]))))))) ; find matching-part -> deduplicate -> insert into `final-body-parts`


(defn symmetrize-body-parts-using-reduce
  "A simpler version using reduce"
  [asym-body-parts]
  (reduce (fn [final-body-parts, part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts)
  )

(defn hit
  [asym-body-parts]
  (let [sym-parts (symmetrize-body-parts-using-reduce asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn -main
  "Hit a hobbit game"
  ([ignored]
   (println "Begin the game")
   (newline)
   (println "Hobbits body parts" asym-hobbit-body-parts)
   (newline)
   (println (matching-part {:name "left-eye" :size 1}))
   (newline)
   (println (symmetrize-body-parts asym-hobbit-body-parts))
   (println (symmetrize-body-parts-using-reduce asym-hobbit-body-parts))
   (println (symmetrize-body-parts asym-hobbit-body-parts))
   )
  ([]
   (println (hit asym-hobbit-body-parts))
   (core-class/into-syntax) ; TODO: Caused by: java.lang.ClassNotFoundException: my-first-clojure-project.core
   )
  )
