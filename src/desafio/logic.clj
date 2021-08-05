(ns desafio.logic
  (:require [desafio.models :as d.models])
  (:use [clojure.pprint]))

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



(defn search-puchases-of-the-month
  [purchases month]
  (->> purchases
       (vals)
       (clojure.pprint/pprint)
       )
  )

(def user (d.models/create-client "Gabriela Lima" 12345678910 "gabriela.lima@nubank.com.br" 2500))
(def purchases-list (:purchases user))

(println "Total por comerciante")
(pprint (search-purchase purchases-list "Panvel"))
(println "Total por valor")
(pprint (search-purchase purchases-list 100))
(println "Total por categoria")
(pprint (total-by-category purchases-list))