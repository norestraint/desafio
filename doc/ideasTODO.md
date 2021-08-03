#A list of ideas to implement or improve on the project

- [ ] Use `atoms` to define a multi-client logic and implement a mutable pucheses list.

- [ ] Use function declaration as variables inside a `let` statement as follow:
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