package br.universidade.projeto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.universidade.projeto.databinding.ActivityListaBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaBinding
    private val db = FirebaseFirestore.getInstance()
    private val produtos = mutableListOf<Produto>()
    private val adapter = ProdutoAdapter(produtos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Produtos cadastrados"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        carregar()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carregar() {
        binding.progress.visibility = View.VISIBLE
        db.collection("Produtos").get()
            .addOnSuccessListener { result ->
                produtos.clear()
                for (doc in result) {
                    produtos.add(
                        Produto(
                            id = doc.id,
                            nome = doc.getString("nome") ?: "",
                            categoria = doc.getString("categoria") ?: "",
                            preco = doc.getDouble("preco") ?: 0.0
                        )
                    )
                }
                adapter.notifyDataSetChanged()
                binding.txtVazio.visibility = if (produtos.isEmpty()) View.VISIBLE else View.GONE
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener { binding.progress.visibility = View.GONE }
    }
}
