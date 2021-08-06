(ns desafio.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [desafio.logic :refer :all]
            [desafio.models :as d.models]
            [desafio.time-helper :as t]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest make-purchase!-test

  (let [client (atom (d.models/create-client "Gabriela Lima" 12345678910 "gabriela.lima@nubank.com.br" 2500))
        purchase1 (d.models/create-purchase "Apple" "Eletrodomesticos" 1000 (t/random-datetime))
        purchase2 (d.models/create-purchase "Panvel" "Saude" 100 (t/random-datetime))
        purchase3 (d.models/create-purchase "Apple" "Eletrodomesticos" 10000 (t/random-datetime))]

    (testing "Make a purchase when there are no other purchases."
      (make-purchase! client purchase1)
      (is (and (some #(= % purchase1) (vals (:purchases @client))) (= 1 (count (:purchases @client))))))

    (testing "Adds a purchase to the list when there are values there."
      (make-purchase! client purchase2)
      (is (and (some #(= % purchase2) (vals (:purchases @client))) (= 2 (count (:purchases @client))))))
    )
  )