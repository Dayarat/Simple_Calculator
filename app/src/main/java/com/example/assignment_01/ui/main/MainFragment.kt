package com.example.assignment_01.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.assignment_01.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        var message = view.findViewById<TextView>(R.id.message)
        message.text= viewModel.result.value.toString()

        viewModel.result.observe(viewLifecycleOwner, Observer  {
            message.text=it.toString()

        })

        var number1EditText = view.findViewById<EditText>(R.id.number1)
        var number2EditText = view.findViewById<EditText>(R.id.number2)

        fun View.hideKeyboard() {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        fun performOperation(operation: (Double, Double) -> Unit) {
            var number1String = number1EditText?.text.toString()
            var number1 = number1String?.toDoubleOrNull()
            var number2String = number2EditText?.text.toString()
            var number2 = number2String?.toDoubleOrNull()

            if ((number1 != null) && (number2 != null)) {
                operation(number1, number2)
            }

            view.hideKeyboard()
        }

        var parentLayout: LinearLayout = view.findViewById(R.id.parentLayout)
        parentLayout.setOnClickListener {
            // Hide the keyboard
            it.hideKeyboard()
        }

        var addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener{
            performOperation{ number1, number2 -> viewModel.add(number1, number2)}
        }

        var minusButton = view.findViewById<Button>(R.id.minusButton)
        minusButton.setOnClickListener{
            performOperation{ number1, number2 -> viewModel.minus(number1, number2)}
        }

        var multiplicationButton = view.findViewById<Button>(R.id.multiplicationButton)
        multiplicationButton.setOnClickListener{
            performOperation{ number1, number2 -> viewModel.multiplication(number1, number2)}
        }

        var divButton = view.findViewById<Button>(R.id.divButton)
        divButton.setOnClickListener{
            performOperation{ number1, number2 -> viewModel.divide(number1, number2)}
        }

        var clearButton = view.findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            number1EditText.setText("")
            number2EditText.setText("")
            message.text = ""
        }


        return view
    }

}