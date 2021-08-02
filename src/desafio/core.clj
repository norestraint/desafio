(ns desafio.core
  (:require [desafio.purchases]
            [desafio.client]
            [desafio.credit-card]
            [desafio.time-helper])
  (:use clojure.pprint)
  )

(comment
  TODO ideas
  - the search function can probably be refactored to use a single thread
  )



(defn sum-of-purchases
  [purchases]
  (->> purchases
       (map :price)
       (reduce +)
       ))

(defn make-hashmap
  [[category purchases]]
  {
   :category            category
   :number-of-purchases (count purchases)
   :sum                 (sum-of-purchases purchases)
   }
  )

(defn total-by-category
  [purchase]
  (->> purchase
       (vals)
       (group-by :category)
       (map make-hashmap)
       ;(into {})
       )
  )

(defn search-purchase-by-value
  [purchases value]
  (let [list
        (->> purchases
             (vals)
             (group-by :price)
             )
        ]
    (get list value "No purchases found for this amount.")
    )
  )

(defn search-purchase-by-merchant
  [purchases value]
  (let [list
        (->> purchases
             (vals)
             (group-by :merchant)
             )
        ]
    (get list value "No purchases found for this merchant.")
    )
  )

(defn search-purchase
  [purchases search-arg]
  (case (str (type search-arg))
    "class java.lang.Long" (search-purchase-by-value purchases search-arg)
    "class java.lang.String" (search-purchase-by-merchant purchases search-arg)
    "Error: Wrong type of argument."
    )
  )

(comment
  (defn search-puchases-of-the-month
    [purchases month]
    (->> purchases
         (vals)
         (clojure.pprint/pprint)
         )
    )
  )


(def user (desafio.client/create-client "Gabriela Lima" 12345678910 "gabriela.lima@nubank.com.br" 2500))
(def purchases-list (:purchases user))

(clojure.pprint/pprint user)