package com.example.simpletodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1.Remove item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that data has been changed
                adapter.notifyDataSetChanged()

                saveItems()

            }
        }

        // 1. Detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            //when the user clicks on the button
//            Log.i("Dharmit","User clicker on a button")
//        }

//        listOfTasks.add("Eat food")
//        listOfTasks.add("Sleep")
        loadItems()

        //Lookup recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing the sample data we created (listOfTasks)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //Attach adapter to recyclerView to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Setup the button input field, so that the user can enter a task and add it to the list

        //Get reference to the button and setup a listener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. Retrieve the text the user has put into @+id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            //2.Add this text to listOfTasks so it gets displayed in the app
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that data has been updated and ask it to add the end of the list
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3.Reset the text field after the add button is clicked
            inputTextField.setText("")

            saveItems()
        }

    }

    //Save the data user had inputted as clearing/closing the app and re-launching it doesn't show the saved list
    //Save data by reading/writing a file

    //Create a method to get the file we need / Get the data file

    fun getDataFile() : File{
        //Every line represents a specific task in our listOfTasks
        return File(filesDir, "data.txt")
    }

    //Load the items by reading every line in the data file

    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }

    //Save items by writing them into a file

    fun saveItems(){
        try{
            //org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
            FileUtils.writeLines(getDataFile(),listOfTasks)
        }

        catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}