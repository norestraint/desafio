(ns desafio.time-helper)
(use '[java-time :exclude [range iterate format max min contains? zero?]])

(defn credit-card-expiration-date
  []
  (plus (local-date) (years 8))
  )

(defn random-date
  []
  (plus (local-date) (days (rand-int 365)))
  )