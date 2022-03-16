(ns my-first-clojure-project.core)

(defn reduce-fn [data]
  (reduce + data)
  )

(defn map-fn [data]
  (map inc data)
  )

(defn no-args []
  (do
    (+ 1 1))
  )

(defn is-even [n]
  (= (mod n 2) 0)
  )

(defn even-or-odd [n]
  (if (is-even n)
    "even"
    "odd"
    )
  )

(defn multi-line-if [n]
  (if (is-even n)
    (do (println "first line even")
        (println "second line even")
        "even"
        )
    (do (println "first line odd")
        (println "second line odd")
        "odd"
        )
    )
  )

(defn let-fn [n]
  (let [x n
        y x]
    y)
  )

(defn hash-map-syntax
  "Checking out hashmap syntax"
  []
  (def mapWithFn {
                  "multi-line-if" multi-line-if
                  "even-or-odd" even-or-odd
                  "map-fn" map-fn
                  "reduce-fn" reduce-fn
                  :keyword no-args
                  })
  (println mapWithFn)
  (println (get mapWithFn "multi-line-if"))
  (println (get mapWithFn "default" "not present"))
  (println ((get mapWithFn "multi-line-if") 3))
  (println ((:keyword mapWithFn)))
  (println (:non-existent-keyword mapWithFn 666))
  )

(defn functions-syntax-basic
  "function syntax"
  []
  (println (reduce-fn [1 2 3 7 13]))
  (println (map-fn [1/2 2/3 3 7/7 13/13 2.8 9.9]))
  (println (map-fn [1.123456789012345799999999999999]))
  (println (map-fn [1.123456789012345899999999999999]))
  (println (no-args))
  (println (even-or-odd 2))
  (println (even-or-odd 3))
  (println (multi-line-if 2))
  (println (multi-line-if 3))
  )

(defn functions-syntax-advance
  "Advance functions syntax"
  [arg]
  (println ((fn [] (str arg " is trying advance functions"))))
  )

(defn functions-syntax
  "Trying out function overloading"
  ([] (functions-syntax-basic))
  ([first-arg] (functions-syntax-advance first-arg))
  )

(defn logical-operators-syntax
  "Logical operators syntax"
  []
  (println (or (= (mod 2 2) 1) (= (mod 3 2) 0)))
  (println (and (= (mod 2 2) 0) (= (mod 3 2) 1)))
  (println ((or + -) 1 3))
  (println ((and + -) 1 3))
  )

; smells like array list
(defn vector-syntax
  "Vector syntax"
  []
  (def some-vector [3 "abc" (fn [] (println "this is a" "function"))])
  (println (get some-vector 0))
  (println (get some-vector 1))
  (println (get some-vector 2))
  ((get some-vector 2))
  (println (conj some-vector 42))
  )

; smells like linked list or stack
(defn list-syntax
  "Playing around with docs and more"
  []
  (def some-list '(3 "abc" (fn [] (println "this is a" "function"))))
  (println some-list)
  (println (nth some-list 2))
  ;((nth some-list 2)) ;TODO: Why is it not a function ?
  (println (conj some-list "will be added to the beginning"))
  )

(defn hashset-syntax
  "Hashset syntax"
  []
  (def set-example #{1 "abc" 3})
  (def set-example-2 (hash-set 1 2 3 4 4 :b))
  (println set-example)
  (println set-example-2)
  (println (contains? set-example "abc"))
  (println (contains? set-example-2 :b))
  (println (contains? set-example-2 nil))
  (println (get set-example-2 :b))
  )

(defn function-with-default-args
  ([name name2]
   (str "Hi, my name is, " name "? My name is, " name2 "?")
   )
  ([name]
   (function-with-default-args name "who")
   )
  ([]
   "Hi, my name is, what? My name is, who?
My name is, chka-chka, Slim Shady
Hi, my name is, huh? My name is, what?
My name is, chka-chka, Slim Shady
   ")
  )

(defn function-with-single-arity
  [param]
  (println param) ;TODO: Why println does not work here ?
  (str "arg" param)
  )

(defn function-with-var-args
  [& params]
  (map function-with-single-arity params)
  )

(defn destructuring-function
  [[first-arg second-arg & other-args] {first-animal :first-animal second-animal :second-animal}]
  (println "First argument: " first-arg)
  (println "Second argument: " second-arg)
  (println "All the other arguments: " other-args)
  (println "Animal map 1:" first-animal "2:" second-animal)
  )

(defn destructing-function-shorter-syntax
  [{:keys [first-animal second-animal] :as animal-map}]
  (println "Animal map 1:" first-animal "2:" second-animal)
  (println "Animal map:" animal-map)
  )

(defn anonymous-functions-syntax []
  (def prefix-function (fn [name] (str "Mr " name)))
  (println (prefix-function "Piotr"))
  (println (map (fn [name] (str "Mr " name)) ["G" "H"]))
  (println (let [scoped-value (map (fn [name] (str "Mr " name)) ["Z" "F"])]
             scoped-value))
  (println (map #(str "Mr Short " %) ["C" "C"]))
  (println (let [scoped-value (map #(str "Mr Short " %) ["X" "Y"])]
             scoped-value))
  (println (#(str "arg-1 : " %1 " arg-2 : " %2 " other-args : " %&)
             "first-argument" "second-argument" "third-argument" "fourth-argument"))
  )

; Copyright John, thanks :)
(defn make-counter
  "function to make a counter"
  [start]
  (let [state (atom start)] ;; define the atom that will be closed over
    (fn [] ;; close over the atom
      (swap! state inc) ;; replace the atom state by applying inc to the current state
      )
    )
  ) ;; return the latest state. This is not fully thread safe, though.

(defn interleve-with-ordinal
  "Add a number before and after each element"
  [prefix-starting-from-number]
  #(let [counter (make-counter (- prefix-starting-from-number 1))
         result (map (fn [name] (let [n (counter)] {:name name :ordinal n})) %)
         ]
     result)
  )

(defn into-syntax
  "Remember that in lists elements are added to the front and in vectors to the end"
  []
  (println (into (hash-map) [[:a 1] [:c 3] [:b 2]]))
  (println (into (sorted-map) [[:a 1] [:c 3] [:b 2]]))
  (println (into {} [[:a 1] [:c 3] [:b 2]]))
  (println (into {} [{:a 1} {:c 3} {:b 2}]))
  (println (into (sorted-map) [{:a 1} {:c 3} {:b 2}]))
  (println (into [] {1 2 3 4}))
  (println (into [] {1 2 3 4}))
  (println (into () '(1 2 3))) ; 3 2 1
  (println (into [] '(1 2 3))) ; 1 2 3
  (println (into [] [1 2 3]))
  (println (into [] (set [1 1 2 2])))
  (println (into '() (set [1 1 2 2])))
  (println (into [] (set ["A" "B" "A" "B"])))
  (println (into '() (set ["A" "B" "A" "B"])))
  )

(defn recursion-syntax
  "loop+recur is more efficient then using the traditional recursion because of tail call optimization"
  []
  (loop [iteration 0]
    (println (str "Iteration " iteration))
    (if (> iteration 3)
      (println "Goodbye!")
      (recur (inc iteration)))))

;(defn fib
;  "Lets try fibonacci using recur O(n2)"
;  [n]
;  (loop [iteration n]
;    (println (str "Iteration " iteration))
;    (if (< iteration 2)
;      (1)
;      (+ (recur (- iteration 1)) (recur (- iteration 2))) ; FIXME: java.lang.UnsupportedOperationException: Can only recur from tail position
;      )))

(defn fib-recur-without-tail-optimization
  "Lets try fibonacci using recur O(n2)"
  [n]
  (println (str "Calculating fib for n=" n))
  (if (<= n 2)
    1
    (+ (fib-recur-without-tail-optimization (- n 1)) (fib-recur-without-tail-optimization (- n 2)))
    ))

(defn fib-recur-with-trampoline
  "Lets try fibonacci using recur O(n2). Using stack optimization "
  [n]
  (if (<= n 2)
    1
    (+' (trampoline fib-recur-with-trampoline (- n 1)) (trampoline fib-recur-with-trampoline (- n 2)))
    ))

(defn fib-for-loop
  "Lets try fibonacci using loop O(n)"
  [n]
  (if (<= n 2)
    1
    (let [current (atom 1)
          prev (atom 1)
          result (atom 1)
          ]
      (last (for [x (range 3 (inc n))]
              ((fn []
                 (reset! result (+' @current @prev))
                 (reset! prev @current)
                 (reset! current @result)
                 @result)))))))

(defn fib-using-map
  [n]
  (if (<= n 2)
    1
    (let [current (atom 1)
          prev (atom 1)
          result (atom 1)]
      (last (map (fn [ignored]
                   (reset! result (+' @current @prev)) ; `+'` auto-promotes to big int
                   (reset! prev @current)
                   (reset! current @result)
                   @result
                   ) (range 3 (inc n))))
      )))

(defn calculate-next-fib-number
  "Expects a vector of [fib(n-2) fib(n-1)] e.g. to calculate fib(3) pass [1 1]"
  [fib-sequence, & ignored]
  (let [[prev, cur] (take-last 2 fib-sequence)]
    into fib-sequence [cur (+' prev cur)]
    )
  )

(defn fib-using-reduce
  [n]
  (if (<= n 2)
    1
    (last (reduce calculate-next-fib-number [1 1] (range 3 (inc n))))))

(defn dotimes-syntax
  [n]
  (let [current (atom 1)
        prev (atom 1)
        result (atom 1)
        ]
    (dotimes [it (- n 2)]
      (
       (fn [it]
         (reset! result (+' @current @prev))
         (reset! prev @current)
         (reset! current @result)
         ) it
       )
      )
    @result
    )
  )

(defn matchers []
  (def matcher (re-matcher #"^left-" "left-eye"))
  (println (re-find matcher))
  (println (re-groups matcher))
  (println (re-seq #"^left-" "left-eye"))
  )

(defn thread-first-syntax [arg]
  (println (-> arg
               (/ 3)
               (* 17)
               (+ 2)
               (- 7)))

  (println (macroexpand `(-> arg
                             (/ 3)
                             (* 17)
                             (+ 2)
                             (- 7))))
  )

(defn thread-last-syntax [arg]
  (println (->> arg
                (/ 3)
                (* 17)
                (+ 2)
                (- 7)
                ))

  (println (macroexpand `(->> arg
                              (/ 3)
                              (* 17)
                              (+ 2)
                              (- 7))))

  )

(defn calculate* []
  (->> (range 10)
       (filter odd?)
       (map #(* % %))
       (reduce +)))

(defn describe-number [n]
  (cond-> []
          (odd? n) (conj "odd")
          (even? n) (conj "even")
          (zero? n) (conj "zero")
          (pos? n) (conj "positive")))

(defn vrange [n]
  (loop [i 0 v []]
    (if (< i n)
      (recur (inc i) (conj v i))
      v)))

(defn vrange-transient-approach [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))


(defn -main
  "Playing around with clojure"
  ([args]
   (when (= args "1st")
     (println args "Hello, World!")
     (println (+ 1 2 3 4))
     (println (first [1 2 3]))
     (println (last [1 2 3]))
     (println (str "Mis " "Koala"))
     (println (let [value (let-fn 7)]
                value))
     )
   (when (= args "2nd")
     (println (nil? 0))
     (println (nil? nil))
     (def value "abc")
     (println value)
     (logical-operators-syntax)
     (functions-syntax)
     (hash-map-syntax)
     (vector-syntax)
     (list-syntax)
     (hashset-syntax)
     (functions-syntax)
     (functions-syntax "Piotr")
     (println (function-with-default-args "what" "who"))
     (println (function-with-default-args "what"))
     (println (function-with-default-args))
     (println (function-with-var-args "1" "2" "3" "4")) ; FIXME: Not working as expected and I dont know why
     (destructuring-function ["Dog", "Cat", "Squirrel", "Badger"] {:first-animal "Dog" :second-animal "Cat"})
     (destructing-function-shorter-syntax {:first-animal "Panda" :second-animal "Koala"})
     (anonymous-functions-syntax)
     (println (range 100))
     (println (interleave [:a :b] [1 2]))
     (println (apply str (interleave [:a :b :c] [1 2 3]))) ; applies each element to a function e.g. str function
     (println ((interleve-with-ordinal 1) ["element-A" "element-B"]))
     (let [values (map #(str "element-" (char %)) (range 65 91))
           result ((interleve-with-ordinal 1) values)]
       (println result)
       (println (apply str result)))
     (into-syntax)
     (recursion-syntax)
     (println (fib-for-loop 2))
     (println (fib-for-loop 3))
     (println (fib-for-loop 10))
     (println (fib-using-map 10))
     (println (fib-using-map (rand-int 30)))
     (matchers)
     (println (fib-recur-with-trampoline 100)) ; FIXME: Doing something wrong here. Will still receive StackOverflow
     (thread-first-syntax 20)
     (thread-last-syntax 20)
     (println (dotimes-syntax 10))
     (into-syntax)
     (def some-vector [1 1 2 3 5])
     (println (take-last 2 some-vector))
     (fib-using-reduce 10)
     (println (fib-using-reduce 10))
     (println (fib-using-reduce 2))
     (println (fib-for-loop 10))
     (def person {:name "Socrates", :age 40})
     (-> person
         (assoc :hair-color :gray)
         (:hair-color)
         name
         println)
     (-> (calculate*)
         println
         )
     (-> "a-string"
         clojure.string/lower-case
         (.indexOf "-")
         println
         )
     (as-> [:foo :bar] v
           (map name v)
           (first v)
           (.substring v 1)
           (println v)
           (println "abc") ; argument being passed is optional and can be insert in any order
           )
     (when-let [counter (:counter {})]
       (-> counter
           (Long/parseLong)
           inc
           println
           )
       )
     (some-> {}
             :counter
             (Long/parseLong)
             inc
             println
             )
     (some-> {:counter "7"}
             :counter
             (Long/parseLong)
             inc
             println
             )
     (-> 2
         describe-number
         println
         )
     (vrange 10000000)
     (vrange-transient-approach 10000000)
     ))
  ([]

   )
  )