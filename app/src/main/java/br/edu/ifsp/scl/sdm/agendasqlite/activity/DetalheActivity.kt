package br.edu.ifsp.scl.sdm.agendasqlite.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.agendasqlite.R
import br.edu.ifsp.scl.sdm.agendasqlite.data.DatabaseHelper
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato

class DetalheActivity : AppCompatActivity() {

    private var contato = Contato()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        contato = this.intent.getSerializableExtra("contato") as Contato

        val nome = findViewById<EditText>(R.id.editTextNome)
        val fone = findViewById<EditText>(R.id.editTextTelefone)
        val email = findViewById<EditText>(R.id.editTextEmail)

        nome.setText(contato.nome)
        fone.setText(contato.fone)
        email.setText(contato.email)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_alterarContato) {
            // Aqui o usuário pode ter alterado alguma informação
            val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
            val fone = findViewById<EditText>(R.id.editTextTelefone).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()

            contato.nome = nome
            contato.fone = fone
            contato.email = email

            if (db.atualizarContato(contato) > 0) {
                Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
            }
            finish()
        }

        if (item.itemId == R.id.action_excluirContato) {
            if (db.apagarContato(contato) > 0) {
                Toast.makeText(this, "Contato Excluído", Toast.LENGTH_LONG).show()
            }
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}