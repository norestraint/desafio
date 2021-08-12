#A list of ideas to implement or improve on the project

- [ ] Add query rules for the database functions(possibly erasing the Datomic id when returning a schema?).

- [ ] Change the type of the value `:price` to BigDecimal.

- [ ] Use a backward query to find all the purchases in a category.

- [ ] Use a forward query to find all the purchases of a client.

- [ ] Change the hashmap of purchases to contains a uuid as the identifier of each purchase.
  
- [ ] Change the `total-by-category` function to return a different hashmap, as follows:

```
{
    :category1 { :number-of-purchases x
                 :total               y }
    :category2 { :number-of-purchases x
                 :total               y }
    ...
}
```

- [x] Reestructure the project so the models stay in one folder and the business logic in another one(`models.clj` and `logic.clj`).

- [x] Use `schemas` to implement the client, credit card and purchases logic.

- [ ] Use `s/constrained` to validate the data of the schemas.

- [ ] Use `defmulti` on the function that searches a purchase by value or merchant name. 

- [x] Use `atoms` or `refs` to define a multi-client logic and implement a mutable pucheses list.

- [x] Use function declaration as variables inside a `let` statement as follow:
```
(defn chega-em
  [hospital departamento pessoa]
  (let [fila (get hospital departamento)
        tamanho-atual (count fila)
        cabe? (< tamanho-atual 5)]
    (if cabe?
      (update hospital departamento conj pessoa)
      (throw (ex-info "Fila já está cheia." { :tentando adicionar: pessoa })))))
```
- [ ] Use exceptions to deal with unwanted behavior. Ex:

```
(if (does-what-i-want)
    (do-whats-expected)
    (throw (ex-info "Something went wrong." { data } cause))
```