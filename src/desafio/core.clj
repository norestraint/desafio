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
   :expiration-date (desafio.time-helper/data-de-validade-cartao)
   :cvv             (generate-number 5)
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
   :purchases   (desafio.purchases/construir-pedidos-completo 3)
   }
  )

(def user (create-client "Jorge Luis" 9233 "jorge@email" 1000))
(def purchase (:purchases user))

(defn total [purchases]
  (->> purchases
       (group-by :category)
       )
  )


(defn break-purchases
  [[_ purchase]]
  purchase
  )

(defn sum-of-purchases
  [purchases]
  (println (key purchase))
  (->> purchases
       (val)
       (map :preco)
       (reduce +)
       (println))
  ;{key (reduce + (map :price purchase))}
  )

(defn total-by-category
  [purchase]
  (->> purchase
       (map break-purchases)
       (group-by :categoria)
       (map sum-of-purchases)
       )
  )

(println purchase)
(total-by-category purchase)