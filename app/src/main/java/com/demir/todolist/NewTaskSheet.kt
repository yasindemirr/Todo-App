package com.demir.todolist

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.demir.todolist.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalTime

class NewTaskSheet(var toDoModel: ToDoModel?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var toDoViewModel: ToDoViewModel
    private var dueTime: LocalTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (toDoModel != null)
        {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(toDoModel!!.name)
            binding.desc.text = editable.newEditable(toDoModel!!.desc)
            if(toDoModel!!.dueTime() != null)
            {
                dueTime = toDoModel!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else {
            binding.taskTitle.text = "New Task"
        }

        toDoViewModel = ViewModelProvider(activity).get(ToDoViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction() {
        val name = binding.name.text.toString().trim()
        val desc = binding.desc.text.toString().trim()
        val dueTimeString = if(dueTime == null) null else ToDoModel.timeFormatter.format(dueTime)
        if(toDoModel == null) {
            if(name.isNotEmpty()){
            val newTask = ToDoModel(name,desc, dueTimeString,null)
            toDoViewModel.addTaskItem(newTask)
                Toast.makeText(requireContext(),"Task created successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Name cannot be empty",Toast.LENGTH_SHORT).show()
            }
        }
        else {
            if (name.isNotEmpty()){
            toDoModel!!.name = name
            toDoModel!!.desc = name
            toDoModel!!.dueTimeString = dueTimeString

            toDoViewModel.updateTaskItem(toDoModel!!)
                Toast.makeText(requireContext(),"Task updated successfully",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(requireContext(),"Name cannot be empty",Toast.LENGTH_SHORT).show()
            }
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

}