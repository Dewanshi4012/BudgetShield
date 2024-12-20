package com.example.budgetshield.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetshield.R
import com.example.budgetshield.databinding.FragmentDashboardBinding
import com.example.budgetshield.databinding.FragmentNewExpenseBinding

class FragmentExpense : Fragment() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    lateinit var expenseBinding: FragmentNewExpenseBinding

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

        expenseBinding.savebtn.setOnClickListener {
            navigateToExpenseFragment()
        }
        expenseBinding.arrow.setOnClickListener{
            navigateToExpenseFragment()
        }
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