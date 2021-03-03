package es.adriiiprieto.notesapp.presentation.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.adriiiprieto.notesapp.databinding.ItemNoteBinding
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

class NotesListAdapter(private var dataSet: List<NoteDomainModel>, private val callbacks: MyClicksListener) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.binding.apply {
            itemNoteTextViewTitle.text = item.title
            itemNoteTextViewBody.text = item.body
            itemNoteButtonEdit.setOnClickListener {
                callbacks.onEditButtonClicked(item)
            }
            itemNoteButtonDelete.setOnClickListener {
                callbacks.onDeleteButtonClicked(item)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateList(newList: List<NoteDomainModel>) {
        dataSet = newList
        notifyDataSetChanged()
    }

    interface MyClicksListener {
        fun onEditButtonClicked(item: NoteDomainModel)
        fun onDeleteButtonClicked(item: NoteDomainModel)
    }
}