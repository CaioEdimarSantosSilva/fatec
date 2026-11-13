package br.universidade.projeto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.universidade.projeto.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtUsuario.text = "Logado como ${auth.currentUser?.email}"
        binding.btnSalvar.setOnClickListener { salvar() }
        binding.btnVerLista.setOnClickListener { startActivity(Intent(this, ListaActivity::class.java)) }
        binding.btnSair.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun salvar() {
        val nome = binding.edtNome.text.toString().trim()
        val categoria = binding.edtCategoria.text.toString().trim()
        val preco = binding.edtPreco.text.toString().replace(",", ".").toDoubleOrNull()
        if (nome.isEmpty()) { binding.edtNome.error = "Informe o nome"; return }
        if (categoria.isEmpty()) { binding.edtCategoria.error = "Informe a categoria"; return }
        if (preco == null) { binding.edtPreco.error = "Informe um preço válido"; return }

        val produto = hashMapOf(
            "nome" to nome,
            "categoria" to categoria,
            "preco" to preco,
            "data" to FieldValue.serverTimestamp()
        )
        binding.btnSalvar.isEnabled = false
        // Coleção "Produtos", documento com ID autogerado
        db.collection("Produtos").add(produto)
            .addOnSuccessListener {
                Toast.makeText(this, "Produto cadastrado!", Toast.LENGTH_SHORT).show()
                binding.edtNome.text?.clear()
                binding.edtCategoria.text?.clear()
                binding.edtPreco.text?.clear()
                binding.edtNome.requestFocus()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener { binding.btnSalvar.isEnabled = true }
    }
}
