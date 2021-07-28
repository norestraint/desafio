(ns desafio.pedidos)

(def pedidos [
              {
               :date        "01/01/2001"
               :preco       100
               :comerciante "Nerdstore"
               :categoria   "Vestuario"
               }
              {
               :date        "12/03/2001"
               :preco       59
               :comerciante "MacDonalds"
               :categoria   "Comida"
               }
              {
               :date        "02/04/2001"
               :preco       35
               :comerciante "Panvel"
               :categoria   "Vestuario"
               }
              {
               :date        "02/01/2001"
               :preco       150
               :comerciante "Hering"
               :categoria   "Vestuario"
               }
              ])
;(println (get pedidos (rand-int 4)))
;(println (repeatedly 10 #(rand-int 10)))
(println (take 2 (repeatedly 10 #(get pedidos (rand-int 4)))))

(->> #(get pedidos (rand-int 4))
     (repeatedly 10)
     (take 2)
     (println)
     )

(comment
(-> (println)
    (take 2)
    (repeatedly 10)
    #(get pedidos (rand-int 4))
    )
)