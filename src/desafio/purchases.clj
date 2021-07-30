(ns desafio.purchases
  (:require desafio.time-helper))

; TODO ideas
; transformar a funcao fazer-desafio.purchases para receber uma lista com as categorias dos desafio.purchases?

(def merchant-list [
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
(defn build-purchase
  (
   []
   (build-purchase (rand-nth merchant-list))
   )
  (
   [[merchant, category]]
   {
    :merchant merchant
    :category   category
    :price       (rand-int 500)
    :date        (desafio.time-helper/random-date)
    }
   ))

(defn make-purchases
  (
   []
   (make-purchases (map inc (range 10)))
   )
  (
   [numeros]
   (if (= 1 (count numeros))
     {(nth numeros 0) (build-purchase)}
     (conj {(nth numeros 0) (build-purchase)} (make-purchases (rest numeros)))
     )
   )
  )

(defn build-purchase-list
  (
   []
   (make-purchases (map inc (range 10)))
   )
  (
   [n]
   (make-purchases (map inc (range n)))
   )
  )