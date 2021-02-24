package com.ixs.testing.roomdatabase.ui.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ixs.testing.roomdatabase.R
import com.ixs.testing.roomdatabase.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter(private val noteList : List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class  NoteHolder(val view: View) :  RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.view.tv_title.text = noteList.get(position).title
        holder.view.tv_note.text = noteList.get(position).note

        holder.view.setOnClickListener {
           val action = HomeFragmentDirections.actionAddNote(noteList.get(position))
            Navigation.findNavController(it).navigate(action)
            //action.N.N = noteList[position]
        }
    }
}