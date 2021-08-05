(ns desafio.models
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [desafio.time-helper :as t]))

(defn generate-number
  ([] (generate-number 10))
  ([n]
   (let [chars-between #(map char (range (int %1) (inc (int %2))))
         chars (concat (chars-between \0 \9))
         number (take n (repeatedly #(rand-nth chars)))]
     (reduce str number))))

(defn get-random-merchant
  []
  (let [merchant-list [["Nerdstore", "Vestuario"]
                       ["Renner", "Vestuario"]
                       ["Burguer King", "Alimentacao"]
                       ["KFC", "Alimentacao"]
                       ["Zaffari", "Alimentacao"]
                       ["Lancheria do Parque", "Alimentacao"]
                       ["Cinemax", "Entretenimento"]
                       ["GNC Cinemas", "Entretenimento"]
                       ["Teatro Sao Pedro", "Entretenimento"]
                       ["Ponto Frio", "Eletrodomesticos"]
                       ["Panvel", "Saude"]
                       ["Farmacia Sao Joao", "Saude"]
                       ["Uber", "Locomocao"]
                       ["99taxi", "Locomocao"]
                       ["Cabify", "Locomocao"]]
        merchant-category (rand-nth merchant-list)]
    {
     :merchant (get merchant-category 0)
     :category (get merchant-category 1)
     }))

(def PosInt (s/pred pos-int?))

(def CreditCard {:number          PosInt
                 :limit           PosInt
                 :expiration-date java.time.LocalDate
                 :cvv             PosInt})

(def Purchase {:merchant s/Str
               :category s/Str
               :price    PosInt
               :date     java.time.LocalDateTime})

(def PurchaseList [Purchase])

(def Client {:name        s/Str
             :cpf         PosInt
             :email       s/Str
             :credit-card CreditCard
             :purchases   PurchaseList})

(s/defn create-purchase :- Purchase
  (
   []
   (let [merchant-category (get-random-merchant)
         merchant (:merchant merchant-category)
         category (:category merchant-category)
         price (rand-int 500)
         date (t/random-datetime)]
     (create-purchase merchant category price date))
   )
  (
   [merchant :- s/Str
    category :- s/Str
    price :- PosInt
    date :- java.time.LocalDateTime]
   {
    :merchant merchant
    :category category
    :price    price
    :date     date
    }
   ))

(s/defn create-random-purchase-list :- PurchaseList
  (
   []
   (create-random-purchase-list 10)
   )
  (
   [n]
   (if (= n 1)
     {(generate-number 8) (create-purchase)}
     (conj {(generate-number 8) (create-purchase)} (create-random-purchase-list (dec n)))
     )
   )
  )

(s/defn create-credit-card :- CreditCard
  [limit :- PosInt]
  {:number          (generate-number)
   :limit           limit
   :expiration-date (t/credit-card-expiration-date)
   :cvv             (generate-number 3)})

(s/defn create-client :- Client
  "Creates a client."
  [name :- s/Str
   cpf :- PosInt
   email :- s/Str
   limit :- PosInt]
  {:name        name
   :cpf         cpf
   :email       email
   :credit-card (create-credit-card limit)
   :purchases   (create-random-purchase-list)})

(pprint (create-purchase "Apple" "Eletrodomesticos" 1200 "92/10/2021"))