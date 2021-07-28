(ns desafio.pedidos
  (:require desafio.tempos))

(def pedidos [
              {
               :date        (desafio.tempos/data-aleatoria)
               :preco       100
               :comerciante "Nerdstore"
               :categoria   "Vestuario"
               }
              {
               :date        (desafio.tempos/data-aleatoria)
               :preco       59
               :comerciante "MacDonalds"
               :categoria   "Comida"
               }
              {
               :date        (desafio.tempos/data-aleatoria)
               :preco       35
               :comerciante "Panvel"
               :categoria   "Medicamento"
               }
              {
               :date        (desafio.tempos/data-aleatoria)
               :preco       150
               :comerciante "Hering"
               :categoria   "Vestuario"
               }
              ])

(defn fazer-pedidos
  (
   []
   (fazer-pedidos (rand-int (inc (count pedidos))))
   )
  (
   [n]
   (->> #(get pedidos (rand-int (count pedidos)))
        (repeatedly 1000)
        (take n)
        )
   )
  )