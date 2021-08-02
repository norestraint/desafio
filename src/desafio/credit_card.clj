(ns desafio.credit-card
  (:require
    [desafio.time-helper]
    ))

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