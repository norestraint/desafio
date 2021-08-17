(ns desafio.models
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [desafio.time-helper :as t]))

(defn uuid [] (java.util.UUID/randomUUID))

(defn generate-number
  ([] (generate-number 8))
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

(def CreditCard {:credit-card/id              java.util.UUID
                 :credit-card/number          PosInt
                 :credit-card/limit           PosInt
                 :credit-card/expiration-date s/Str
                 :credit-card/cvv             PosInt})

(def Purchase {:purchase/id       java.util.UUID
               :purchase/merchant s/Str
               :purchase/category s/Str
               :purchase/price    BigDecimal
               :purchase/datetime  s/Str})

(s/def Client {:client/id                           java.util.UUID
               :client/name                         s/Str
               :client/cpf                          s/Str
               :client/email                        s/Str
               (s/optional-key :client/credit-card) CreditCard
               (s/optional-key :client/purchases)   [Purchase]})

(s/defn create-purchase :- Purchase
  (
   []
   (let [merchant-category (get-random-merchant)
         merchant (:merchant merchant-category)
         category (:category merchant-category)
         price 100M
         date (t/random-datetime)]
     (create-purchase merchant category price date))
   )
  (
   [merchant :- s/Str
    category :- s/Str
    price :- BigDecimal
    date :- s/Str]
   {
    :purchase/id       (uuid)
    :purchase/merchant merchant
    :purchase/category category
    :purchase/price    price
    :purchase/datetime  date
    }
   ))

(s/defn create-random-purchase-list
  ([]
   (create-random-purchase-list 10))
  ([n]
   (if (= n 1)
     {(uuid) (create-purchase)}
     (conj {(uuid) (create-purchase)} (create-random-purchase-list (dec n)))
     )))

(s/defn create-credit-card :- CreditCard
  [limit :- PosInt]
  {:credit-card/id              (uuid)
   :credit-card/number          (Integer. (generate-number))
   :credit-card/limit           limit
   :credit-card/expiration-date (t/credit-card-expiration-date)
   :credit-card/cvv             (Integer. (generate-number 3))})

(s/defn create-client :- Client
  "Creates a client."
  [name :- s/Str
   cpf :- s/Str
   email :- s/Str]
  {:client/id    (uuid)
   :client/name  name
   :client/cpf   cpf
   :client/email email})