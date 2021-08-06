(ns desafio.logic
  (:require [desafio.models :as d.models]
            [schema.core :as s]
            [desafio.time-helper :as t])
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
  [client]
  (if-let [purchase (:purchases client)]
    (->> purchase
         (vals)
         (group-by :category)
         (map make-hashmap)
         )
    "No purchases found."))

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
  [purchases merchant]
  (let [list
        (->> purchases
             (vals)
             (group-by :merchant)
             )]
    (get list merchant "No purchases found for this merchant.")))

(defn search-purchase
  [client search-arg]
  (if-let [purchases (:purchases client)]
    (do
      (case (str (type search-arg))
        "class java.lang.Long" (search-purchase-by-value purchases search-arg)
        "class java.lang.String" (search-purchase-by-merchant purchases search-arg)
        "Error: Wrong type of argument."
        ))
    "No purchases found."))


; (defn search-puchases-of-the-month
; [client]
; (if-let [purchases (:purchases client)]
;   (do (->> purchases
;            (vals)
;            (clojure.pprint/pprint)
;            ))
;   "No purchases found."))

(s/defn make-purchase                                       ; :- (d.models/Client)
  [client purchase]
  (if-let [old-purchases (:purchases client)]
    (assoc client :purchases (conj old-purchases {(d.models/generate-number 8) purchase}))
    (assoc client :purchases {(d.models/generate-number 8) purchase})))

(s/defn make-purchase!
  [client purchase]
  (swap! client make-purchase purchase))

(defn test-logic
  []
  (let [client (atom (d.models/create-client "Gabriela Lima" 12345678910 "gabriela.lima@nubank.com.br" 2500))
        purchase1 (d.models/create-purchase "Apple" "Eletrodomesticos" 1000 (t/random-datetime))
        purchase2 (d.models/create-purchase "Panvel" "Saude" 100 (t/random-datetime))
        purchase3 (d.models/create-purchase "Apple" "Eletrodomesticos" 10000 (t/random-datetime))]

    (println "Total por comerciante: Apple")
    (pprint (search-purchase @client "Apple"))
    (println "Adicionando compra na Apple...")
    (make-purchase! client purchase1)
    (println "Total por comerciante: Apple")
    (pprint (search-purchase @client "Apple"))

    (println "Total por valor: 100")
    (pprint (search-purchase @client 100))
    (println "Fazendo compra no valor de: 100")
    (make-purchase! client purchase2)
    (println "Total por valor: 100")
    (pprint (search-purchase @client 100))

    (println "Total por categoria")
    (pprint (total-by-category @client))
    (println "Adicionando compra do setor: Eletrodomesticos")
    (make-purchase! client purchase3)
    (println "Total por categoria")
    (pprint (total-by-category @client))
    ))