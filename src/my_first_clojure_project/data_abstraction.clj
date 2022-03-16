(ns my-first-clojure-project.data-abstraction)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(def identities-map {:Batman "Bruce Wayne"
                     :Spider-Man "Peter Parker"
                     :Santa "Your mom"
                     (keyword "Easter Bunny") "Your dad"
                     }
  )

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true, :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(defn -main
  ([& ignored]
   (println (cons "a" ["b" "c"]))
   (println (map :alias identities))
   (println (seq identities))
   (println (reduce (fn [new-map [key val]]
                      (assoc new-map key (str val "'ish")))
                    {}
                    {:cucumber "fresh" :lemon "bitter"}))
   (println (reduce-kv #(assoc %1 %2 (str %3 "'ish"))
                       {}
                       {:cucumber "fresh" :lemon "bitter"}))
   (println (reduce #(assoc %1 (first %2) (str (last %2) "'ish"))
                    {}
                    {:cucumber "fresh" :lemon "bitter"}))
   (println (assoc (assoc {} :cucumber (str "fresh" "'ish")) :lemon (str "bitter" "'ish")))
   (println identities-map)
   (def only-batman (reduce (fn [results [key value]]
                              (if (= value "Bruce Wayne")
                                (assoc results key value)
                                results
                                ))
                            {}
                            identities-map
                            ))
   (println only-batman)
   (println (take 2 identities))
   (println (drop 2 identities))
   (println (take-while #(<= (:month %) 1) food-journal))
   (println (drop-while #(<= (:month %) 3) food-journal))
   (println (filter #(> (:human %) 4.2) food-journal))
   (-> food-journal
       ((fn [food] (filter #(> (:human %) 4.2) food)))
       (#(map :critter %))
       (#(apply max %))
       println
       )

   (-> food-journal
       ((fn [food] (take-while #(< (:month %) 3) food)))
       ((fn [food] (drop-while #(< (:month %) 2) food)))
       (#(map :month %))
       (#(apply max %))
       println
       )

   (-> food-journal
       ((fn [food] (some #(> (:critter %) 3.8) food)))
       println
       )

   (-> food-journal
       ((fn [food] (some #(> (:critter %) 3.9) food)))
       println
       ) ;nil

   (println (some identity food-journal))

   (-> food-journal
       ((fn [food] (some #(and (> (:critter %) 3.8) %) food)))
       println
       ) ;nil
   (-> food-journal
       (#(sort-by first %))
       println
       )

   (-> food-journal
       (#(sort-by :human %))
       reverse
       println
       )
   (-> food-journal
       (#(concat % [{:month 9 :day 13 :human 6.6 :critter 6.6}]))
       count
       println
       )
   (println (vampire-related-details 0))
   (println (vampire-related-details 1))
   (println (time (vampire-related-details 1)))
   (println (time (def mapped-details (map vampire-related-details (range 0 1000000)))))
   (-> (map vampire-related-details (range 0 50))
       (#(time (first %))) ; 32 seconds because clojure chunks lazy sequency when realizing them. In this case I guess it was 32 elements
       println
       )
   )
  ([]
   (->> (take 100 (repeat "na"))
        (#(time (concat % ["Batman!"])))
        (#(time (println %)))
        )
   ))