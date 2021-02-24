package com.ixs.testing.roomdatabase.ui.Fragment

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.ixs.testing.roomdatabase.R
import com.ixs.testing.roomdatabase.db.AppDataBase
import com.ixs.testing.roomdatabase.db.Note
import com.ixs.testing.roomdatabase.ui.BaseFragment
import com.ixs.testing.roomdatabase.ui.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private  var notes : Note? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            notes = AddNoteFragmentArgs.fromBundle(it).note
            ettitle.setText(notes?.title)
            etnote.setText(notes?.note)


        }
        fab_save.setOnClickListener { view->
            SaveNote(view)
        }
    }

    private fun SaveNote(view: View) {
        var title = ettitle.text.toString().trim()
        var Note = etnote.text.toString().trim()

        if(title.isNullOrEmpty()){
            ettitle.error = "Required Title"
            ettitle.requestFocus()
            return
        }
        if(Note.isNullOrEmpty()){
            etnote.error = "Required Note"
            etnote.requestFocus()
            return
        }


        launch {
            context?.let {
                val noteobj = Note(title, Note)
                if(notes == null){
                    AppDataBase(it).getNoteDao().SaveNote(noteobj)
                    it.toast("Note Saved")
                }
                else{
                    noteobj.id = notes!!.id
                    AppDataBase(it).getNoteDao().UpdateNote(noteobj)
                    it.toast("Note Updated")
                }

                var action = AddNoteFragmentDirections.actionSaveNote()
                Navigation.findNavController(view).navigate(action)
            }
        }
        //CallAsync(noteobj)

    }

//    private fun CallAsync(note : Note){
//        class AsynTask : AsyncTask<Void,Void,Void>(){
//            override fun doInBackground(vararg p0: Void?): Void? {
//                AppDataBase(activity!!).getNoteDao().SaveNote(note)
//                return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(activity,"Note Saved",Toast.LENGTH_SHORT).show()
//            }
//        }
//        AsynTask().execute()
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> if(notes != null) deleteNote() else context?.toast("No Data to Delete")
        }
        return super.onOptionsItemSelected(item)


    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){_, _ ->
                    launch {
                        AppDataBase(context).getNoteDao().DeleteNote(notes!!)
                        var action = AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(requireView()).navigate(action)

                    }

            }
            setNegativeButton("No"){_,_ ->

            }
        }.create().show()


    }
}