(ns desafio.db
  (:require [datomic.api :as d]
            [desafio.models :as model]
            [schema.core :as s]
            [desafio.models :as model]
            [clojure.walk :as walk])
  (:use clojure.pprint))


(def db-uri "datomic:dev://localhost:4334/nu-challenge")

(defn create-database! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-database! []
  (d/delete-database db-uri))

(def schema [;Client schema
             {:db/ident       :client/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "Client's unique identifier inside the database"}
             {:db/ident       :client/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Client's name."}
             {:db/ident       :client/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Client's unique national identifier."}
             {:db/ident       :client/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Client's email address."}
             {:db/ident       :client/credit-card
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one
              :db/doc         "Client's credit card info."}
             {:db/ident       :client/purchases
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/many
              :db/doc         "List of purchases of the client."}

             ;Credit card schema
             {:db/ident       :credit-card/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "Credit card unique identifier."}
             {:db/ident       :credit-card/number
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc         "Credit card number."}
             {:db/ident       :credit-card/cvv
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc         "Credit card purchase code."}
             {:db/ident       :credit-card/limit
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc         "Credit card limit."}
             {:db/ident       :credit-card/expiration-date
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Credit card expiration date."}

             ;Purchase schema
             {:db/ident       :purchase/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "Purchase unique identifier."}
             {:db/ident       :purchase/merchant
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Name of the merchant where the purchase was made."}
             {:db/ident       :purchase/category
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Category of business where the purchase was made."}
             {:db/ident       :purchase/price
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "Cost of the purchase."}
             {:db/ident       :purchase/instant
              :db/valueType   :db.type/instant
              :db/cardinality :db.cardinality/one
              :db/doc         "Moment of the purchase."}])

(defn load-schema!
  [conn]
  (d/transact conn schema))
