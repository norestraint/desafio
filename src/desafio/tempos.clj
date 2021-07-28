(ns desafio.tempos)
(use '[java-time :exclude [range iterate format max min contains? zero?]])

(defn data-de-validade-cartao
  []
  (plus (local-date) (years 8))
  )

(defn data-aleatoria
  []
  (plus (local-date) (days (rand-int 365)))
  )