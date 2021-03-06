package ufc.br.carrinhocompras.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Carrinho implements Serializable {

    private List<Item> items;


    public Carrinho() {
        items = new ArrayList<>();
    }

    public void adicionar(Item item) {
        items.add(item);
    }

    public void remove(int position) {
        items.remove(position);
    }

    public void limpaLista(List<Item> items) {
        items.removeAll(items);
    }

    public List<Item> pegaListaItens() {

        return items;
    }
}

