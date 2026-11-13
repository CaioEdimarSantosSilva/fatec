package br.universidade.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.universidade.projeto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener { entrar() }
        binding.btnCadastrar.setOnClickListener { cadastrar() }
        binding.btnRecuperar.setOnClickListener { recuperarSenha() }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) irParaMain()
    }

    private fun email() = binding.edtEmail.text.toString().trim()
    private fun senha() = binding.edtSenha.text.toString()

    private fun emailValido(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email()).matches()) {
            binding.edtEmail.error = "Informe um e-mail válido"
            return false
        }
        return true
    }

    private fun carregando(on: Boolean) {
        binding.progress.visibility = if (on) View.VISIBLE else View.GONE
        binding.btnEntrar.isEnabled = !on
        binding.btnCadastrar.isEnabled = !on
        binding.btnRecuperar.isEnabled = !on
    }

    private fun entrar() {
        if (!emailValido()) return
        if (senha().isEmpty()) { binding.edtSenha.error = "Informe a senha"; return }
        carregando(true)
        auth.signInWithEmailAndPassword(email(), senha()).addOnCompleteListener(this) { task ->
            carregando(false)
            if (task.isSuccessful) irParaMain() else erro("Falha ao entrar", task.exception)
        }
    }

    private fun cadastrar() {
        if (!emailValido()) return
        if (senha().length < 6) { binding.edtSenha.error = "A senha deve ter ao menos 6 caracteres"; return }
        carregando(true)
        auth.createUserWithEmailAndPassword(email(), senha()).addOnCompleteListener(this) { task ->
            carregando(false)
            if (task.isSuccessful) {
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                irParaMain()
            } else erro("Falha ao cadastrar", task.exception)
        }
    }

    private fun recuperarSenha() {
        if (!emailValido()) return
        carregando(true)
        auth.sendPasswordResetEmail(email()).addOnCompleteListener(this) { task ->
            carregando(false)
            if (task.isSuccessful) {
                AlertDialog.Builder(this)
                    .setTitle("E-mail enviado")
                    .setMessage("Enviamos um link de redefinição de senha para ${email()}.")
                    .setPositiveButton("OK", null).show()
            } else erro("Falha ao enviar e-mail", task.exception)
        }
    }

    private fun erro(titulo: String, e: Exception?) {
        val msg = when ((e as? FirebaseAuthException)?.errorCode) {
            "ERROR_INVALID_CREDENTIAL", "ERROR_WRONG_PASSWORD", "ERROR_USER_NOT_FOUND" -> "E-mail ou senha incorretos"
            "ERROR_EMAIL_ALREADY_IN_USE" -> "Este e-mail já está cadastrado"
            "ERROR_WEAK_PASSWORD" -> "Senha muito fraca"
            else -> e?.localizedMessage ?: "Erro desconhecido"
        }
        Toast.makeText(this, "$titulo: $msg", Toast.LENGTH_LONG).show()
    }

    private fun irParaMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
