(ns desafio.core
  (:require [desafio.pedidos]
            [desafio.tempos])
  )
; Necessario pra usar a biblioteca de tempo, tive que fazer uma mega "gambiarra" pra nao dar conflito entre funcoes do pacote e do clojure.
;(use '[java-time :exclude [range iterate format max min contains? zero?]])

; Roubei daqui https://stackoverflow.com/questions/27053726/how-to-generate-random-password-with-the-fixed-length-in-clojure
(defn gerar-numero
  ([] (gerar-numero 10))
  ([n]
   (let [chars-between #(map char (range (int %1) (inc (int %2))))
         chars (concat (chars-between \0 \9))
         numero (take n (repeatedly #(rand-nth chars)))]
     (reduce str numero))))

(defn criar-cartao
  "Cria um hashmap com as informações do cartão do cliente."
  [limite]
  {
   :numero   (gerar-numero)
   :limite   limite
   :validade (desafio.tempos/data-de-validade-cartao)
   :cvv      (gerar-numero 3)
   }
  )

(defn criar-cliente
  "Cria um hashmap com os dados do cliente."
  [nome cpf email limite]
  {
   :nome    nome
   :cpf     cpf
   :email   email
   :cartao  (criar-cartao limite)
   :pedidos (desafio.pedidos/fazer-pedidos 10)
   }
  )

(defn total [pedidos]
  (->> pedidos
       (group-by :categoria)
       )
  )

(def cliente (criar-cliente "Jorge Luis" 9233 "jorge@email" 1000))
(def pedido (total (:pedidos cliente)))

(defn total-do-pedido
  [[chave pedido]]
  {chave (reduce + (map :preco pedido))}
  )
(defn total-por-categoria
  [pedido]
  (->> pedido
       (map total-do-pedido)
       )
  )