package com.ixs.testing.roomdatabase.ui.Fragment

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ixs.testing.roomdatabase.R
import com.ixs.testing.roomdatabase.db.AppDataBase
import com.ixs.testing.roomdatabase.db.Note
import kotlinx.android.synthetic.main.fragment_add_note.*


class AddNoteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_save.setOnClickListener {
            SaveNote()
        }
    }

    private fun SaveNote() {
        var title = ettitle.text.toString().trim()
        var note = etnote.text.toString().trim()

        if(title.isNullOrEmpty()){
            ettitle.error = "Required Title"
            ettitle.requestFocus()
            return
        }
        if(note.isNullOrEmpty()){
            etnote.error = "Required Note"
            etnote.requestFocus()
            return
        }

        val noteobj = Note(title,note)
        CallAsync(noteobj)

    }

    private fun CallAsync(note : Note){
        class AsynTask : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                AppDataBase(activity!!).getNoteDao().SaveNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(activity,"Note Saved",Toast.LENGTH_SHORT).show()
            }
        }
        AsynTask().execute()
    }
}