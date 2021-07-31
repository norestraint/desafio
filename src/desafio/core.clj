(ns desafio.core
  (:require [desafio.purchases]
            [desafio.time-helper])
  )

; Roubei daqui https://stackoverflow.com/questions/27053726/how-to-generate-random-password-with-the-fixed-length-in-clojure
(defn generate-number
  ([] (generate-number 10))
  ([n]
   (let [chars-between #(map char (range (int %1) (inc (int %2))))
         chars (concat (chars-between \0 \9))
         number (take n (repeatedly #(rand-nth chars)))]
     (reduce str number))))

(defn create-credit-card
  "Cria um hashmap com as informações do cartão do cliente."
  [limit]
  {
   :number          (generate-number)
   :limit           limit
   :expiration-date (desafio.time-helper/credit-card-expiration-date)
   :cvv             (generate-number 3)
   }
  )

(defn create-client
  "Cria um hashmap com os dados do cliente."
  [name cpf email limit]
  {
   :name        name
   :cpf         cpf
   :email       email
   :credit-card (create-credit-card limit)
   :purchases   (desafio.purchases/build-purchase-list 20)
   }
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
   category (sum-of-purchases purchases)
   }
  )

(defn total-by-category
  [purchase]
  (->> purchase
       (vals)
       (group-by :category)
       (map make-hashmap)
       (into {})
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

