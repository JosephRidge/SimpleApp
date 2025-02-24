package com.jayr.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jayr.task.data.model.Task
import com.jayr.task.databinding.FragmentCreateTaskBinding
import com.jayr.task.databinding.FragmentFirstBinding

class CreateTaskFragment : Fragment() {
    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
    private val database = Firebase.database
    val myDBRef = database.getReference("tasks")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        val title = _binding!!.title
        val description =  _binding!!.decription
        val date =  _binding!!.date
        val progressBar =  _binding!!.progressIndicator
        val createButton =  _binding!!.createTaskBtn

        createButton.setOnClickListener() {
            createTask(title, description, date, progressBar)
        }

        return binding.root
    }

    private fun createTask(title:EditText, descr: EditText, date:EditText, progressBar:ProgressBar){
        val title = title.text.trim().toString()
        val description = descr.text.trim().toString()
        val date = date.text.trim().toString()

        showProgressIndicator(progressBar, true)
        if( title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()){
            val task:Task = Task(
                title=title,
                details=description,
                date=date
            )
            myDBRef.setValue(task)

        }else{
            showProgressIndicator(progressBar, false)
            Toast.makeText(
                layoutInflater.context,
                "Missing Inputs",
                Toast.LENGTH_SHORT,
            ).show()

        }


    }
    private fun showProgressIndicator(progressBar: ProgressBar, state:Boolean){
        if(state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}