(ns desafio.core)
; Necessario pra usar a biblioteca de tempo, tive que fazer uma mega "gambiarra" pra nao dar conflito entre funcoes do pacote e do clojure.
(use '[java-time :exclude [range iterate format max min contains? zero?]])

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
   :validade (plus (local-date) (years 8))                  ; Nao sei porque ele nao reconhece as funcoes mas tá funcionando.
   :cvv (gerar-numero 3)
   }
  )

(defn criar-cliente
  "Cria um hashmap com os dados do cliente."
  [nome cpf email limite]
  {
   :nome   nome
   :cpf    cpf
   :email  email
   :cartao (criar-cartao limite)
   :pedidos []
   }
  )

(def cliente (criar-cliente "Jorge Luis" 9233 "jorge@email" 1000))
(println cliente)
