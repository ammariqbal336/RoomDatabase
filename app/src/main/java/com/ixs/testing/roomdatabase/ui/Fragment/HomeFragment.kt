package com.ixs.testing.roomdatabase.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ixs.testing.roomdatabase.R
import com.ixs.testing.roomdatabase.db.AppDataBase
import com.ixs.testing.roomdatabase.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_notes.setHasFixedSize(true)
        rv_notes.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = AppDataBase(it).getNoteDao().getNote()
                rv_notes.adapter = NoteAdapter(notes!!)
            }

        }

        fab_add.setOnClickListener {
            val action_add = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action_add)
        }

    }
}