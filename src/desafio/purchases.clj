(ns desafio.purchases
  (:require desafio.time-helper))

; TODO ideas
; transformar a funcao fazer-desafio.purchases para receber uma lista com as categorias dos desafio.purchases?

(def comerciantes [
                   ["Nerdstore", "Vestuario"]
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
                   ["Cabify", "Locomocao"]
                   ])
(defn construir-pedido
  (
   []
   (construir-pedido (rand-nth comerciantes))
   )
  (
   [[comerciante, categoria]]
   {
    :comerciante comerciante
    :categoria   categoria
    :preco       (rand-int 500)
    :data        (desafio.time-helper/data-aleatoria)
    }
   ))

(defn construir-pedidos
  (
   []
   (construir-pedidos (map inc (range 10)))
   )
  (
   [numeros]
   (if (= 1 (count numeros))
     {(nth numeros 0) (construir-pedido)}
     (conj {(nth numeros 0) (construir-pedido)} (construir-pedidos (rest numeros)))
     )
   )
  )

(defn construir-pedidos-completo
  (
   []
   (construir-pedidos (map inc (range 10)))
   )
  (
   [n]
   (construir-pedidos (map inc (range n)))
   )
  )
(println (construir-pedidos-completo 5))
(def pedidos {
              {
               :date        (desafio.time-helper/data-aleatoria)
               :preco       100
               :comerciante "Nerdstore"
               :categoria   "Vestuario"
               }
              {
               :date        (desafio.time-helper/data-aleatoria)
               :preco       59
               :comerciante "MacDonalds"
               :categoria   "Comida"
               }
              {
               :date        (desafio.time-helper/data-aleatoria)
               :preco       35
               :comerciante "Panvel"
               :categoria   "Medicamento"
               }
              {
               :date        (desafio.time-helper/data-aleatoria)
               :preco       150
               :comerciante "Hering"
               :categoria   "Vestuario"
               }
              })

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

(println (fazer-pedidos 10))