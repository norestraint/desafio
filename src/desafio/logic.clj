(ns desafio.logic
  (:require [schema.core :as s]
            [desafio.db :as db]
            [clojure.string :as str])
  (:use [clojure.pprint]))

(s/defn total-by-category
  [db
   uuid :- java.util.UUID]
  (if-let [client (db/get-client-by-uuid db uuid)]
    (if-let [purchases (get client :client/purchases)]
      (group-by #(get % :purchase/category) purchases)
      "No purchases found.")
    "No client found."))

(defn search-purchase-by-value
  [purchases value]
  (get (group-by #(get % :purchase/price) purchases) value)
  )

(defn search-purchase-by-merchant
  [purchases merchant]
  (get (group-by #(get % :purchase/merchant) purchases) merchant)
  )

(s/defn search-purchase
  [db
   uuid :- java.util.UUID
   search-arg]
  (if-let [client (db/get-client-by-uuid db uuid)]
    (if-let [purchases (get client :client/purchases)]
      (do
        (case (str (type search-arg))
          "class java.math.BigDecimal" (search-purchase-by-value purchases search-arg)
          "class java.lang.String" (search-purchase-by-merchant purchases search-arg)
          (throw (ex-info "invalid parameter. need string or num." {:parameter search-arg}))
          ))
      "No purchases found.")
    "No client found."))

(defn sum-of-purchases
  [purchases]
  (->> purchases
       (map :purchase/price)
       (reduce +)))

(defn filter-purchases-by-month
  [date-period purchases]
  (let [sorted-purchases (group-by :purchase/datetime purchases)
        search-month (nth (str/split date-period #"/") 0)
        search-year (nth (str/split date-period #"/") 1)
        search-filter (fn [[key _]]
                        (let
                          [this-month (nth (str/split key #"/") 1)
                           this-year (nth (str/split key #"/") 2)]
                          (and (= this-month search-month) (= this-year search-year))))
        result (filter search-filter sorted-purchases)
        ]
    (if (not (empty? result))
      (into [] (flatten (vals result)))
      nil)))

(s/defn bill-of-the-month
  [db
   uuid :- java.util.UUID
   date-period :- s/Str]
  (if-let [client (db/get-client-by-uuid db uuid)]
    (if-let [purchases (get client :client/purchases)]
      (if-let [month-purchases (filter-purchases-by-month date-period purchases)]
        {:month               (nth (str/split date-period #"/") 0)
         :year                (nth (str/split date-period #"/") 1)
         :number-of-purchases (count month-purchases)
         :total               (sum-of-purchases month-purchases)}
        (str "No purchases found for the month of " date-period))
      "No purchases found.")
    "No client found."))