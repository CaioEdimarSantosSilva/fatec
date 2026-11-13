package br.universidade.projeto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.universidade.projeto.databinding.ItemProdutoBinding
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter(private val itens: List<Produto>) : RecyclerView.Adapter<ProdutoAdapter.VH>() {
    class VH(val b: ItemProdutoBinding) : RecyclerView.ViewHolder(b.root)

    private val moeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = itens[position]
        holder.b.txtNome.text = p.nome
        holder.b.txtCategoria.text = p.categoria
        holder.b.txtPreco.text = moeda.format(p.preco)
    }

    override fun getItemCount() = itens.size
}
