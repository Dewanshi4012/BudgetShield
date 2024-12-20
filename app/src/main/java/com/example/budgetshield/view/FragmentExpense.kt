package com.example.budgetshield.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.budgetshield.R
import com.example.budgetshield.databinding.FragmentDashboardBinding
import com.example.budgetshield.databinding.FragmentNewExpenseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class FragmentExpense : Fragment() {

    lateinit var mContext: Context
    lateinit var expenseBinding: FragmentNewExpenseBinding

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        expenseBinding = FragmentNewExpenseBinding.inflate(inflater,container,false)
        return expenseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseBinding.ivCalendar.setOnClickListener {
            showDatePickerDialog()
        }

        // Save button action
        expenseBinding.savebtn.setOnClickListener {
            saveExpenseData()
        }

        expenseBinding.arrow.setOnClickListener {
            navigateToExpenseFragment()
        }
    }

    private fun saveExpenseData() {
        val selectedRadioButtonId = expenseBinding.radiogrp.checkedRadioButtonId
        val selectedRadioButton: RadioButton? = view?.findViewById(selectedRadioButtonId)

        val typeOfRecord = selectedRadioButton?.text.toString()

        // Get the selected date from the TextView
        val selectedDate = expenseBinding.tvDate.text.toString()

        // Get the description from the EditText
        val description = expenseBinding.etDescription.text.toString()

        // Get the amount from the EditText
        val amount = expenseBinding.amount.text.toString().toDoubleOrNull()

        // Validate that all fields are filled
        if (typeOfRecord.isEmpty() || selectedDate.isEmpty() || description.isEmpty() || amount == null) {
            Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the current user
        val currentUser = auth.currentUser
        val userId = currentUser?.uid ?: return

        // Create a map with the data to save in Firestore
        val expenseData = hashMapOf(
            "type" to typeOfRecord,
            "date" to selectedDate,
            "description" to description,
            "amount" to amount
        )

        // Save the data to Firestore under the user's UID
        db.collection("expenses")
            .document(userId)
            .collection("records")
            .add(expenseData)
            .addOnSuccessListener {
                Toast.makeText(mContext, "Expense saved successfully", Toast.LENGTH_SHORT).show()
                navigateToExpenseFragment() // Navigate after saving data
            }
            .addOnFailureListener {
                Toast.makeText(mContext, "Failed to save data", Toast.LENGTH_SHORT).show()
            }

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Date Picker Dialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                expenseBinding.tvDate.text = selectedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun navigateToExpenseFragment() {
        val fragmentDash = FragmentDash.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentDash)
            .addToBackStack(null) // Adds this transaction to the back stack
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentExpense()
    }
}