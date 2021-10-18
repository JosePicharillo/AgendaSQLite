package br.edu.ifsp.scl.sdm.agendasqlite.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.agendasqlite.R
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato

class ContatoAdapter(val contatosLista: ArrayList<Contato>) :
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>(), Filterable {

    var listener: ContatoListener? = null

    var contatosListaFiltrerable = ArrayList<Contato>() //Filtro Pesquisa

    init {
        this.contatosListaFiltrerable = contatosLista
    }

    fun setClickListener(listener: ContatoListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContatoAdapter.ContatoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contato_celula, parent, false)
        return ContatoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContatoAdapter.ContatoViewHolder, position: Int) {
        holder.nomeVH.text = contatosListaFiltrerable[position].nome
        holder.foneVH.text = contatosListaFiltrerable[position].fone
    }

    override fun getItemCount(): Int {
        return contatosListaFiltrerable.size
    }

    inner class ContatoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeVH = view.findViewById<TextView>(R.id.nome)
        val foneVH = view.findViewById<TextView>(R.id.fone)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface ContatoListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    contatosListaFiltrerable = contatosLista
                } else {
                    val resultList = ArrayList<Contato>()
                    for (row in contatosLista){
                        if (row.nome.lowercase().contains(p0.toString().lowercase())){
                            resultList.add(row)
                        }
                    }
                    contatosListaFiltrerable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contatosListaFiltrerable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                contatosListaFiltrerable = p1?.values as ArrayList<Contato>
                notifyDataSetChanged()
            }
        }
    }

}