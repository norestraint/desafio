(ns desafio.client
  (:require
    [desafio.credit-card]
    [desafio.purchases]
    )
  )

(defn create-client
  "Cria um hashmap com os dados do cliente."
  [name cpf email limit]
  {
   :name        name
   :cpf         cpf
   :email       email
   :credit-card (desafio.credit-card/create-credit-card limit)
   :purchases   (desafio.purchases/build-purchase-list 20)
   }
  )
