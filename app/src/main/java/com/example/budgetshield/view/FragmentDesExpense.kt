package com.example.budgetshield.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetshield.R
import com.example.budgetshield.data.RecordModel
import com.example.budgetshield.databinding.ExpenseDesBinding
import com.example.budgetshield.databinding.FragmentNewExpenseBinding

class FragmentDesExpense: Fragment() {
    private lateinit var record: RecordModel
    private lateinit var binding: ExpenseDesBinding
    companion object {
        @JvmStatic
        fun newInstance(record: RecordModel) = FragmentDesExpense().apply {
            arguments = Bundle().apply {
                putSerializable("record", record) // Pass the RecordModel as an argument
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = ExpenseDesBinding.inflate(inflater, container, false)

        // Get the RecordModel from arguments
        record = arguments?.getSerializable("record") as? RecordModel
            ?: return binding.root

        // Now you can use the record to populate the UI, for example:
        binding.descript.text = record.description
        binding.expense.text = record.type
        binding.amount.text = record.amount
        binding.ExDate.text = record.date

        return binding.root
    }


}