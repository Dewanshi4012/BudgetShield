package com.example.budgetshield.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetshield.R
import com.example.budgetshield.data.RecordModel
import com.example.budgetshield.databinding.FragmentDashboardBinding

class FragmentDash : Fragment() {

    lateinit var listAdapter : ListAdapter
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    lateinit var dashboardBinding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardBinding = FragmentDashboardBinding.inflate(inflater,container,false)
        return dashboardBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<RecordModel>(
            RecordModel("Expense #1", "20 Dec, 2024", "Electricity Bill", "Rs 2200"),
            RecordModel("Income #1", "19 Dec, 2024", "Internship", "Rs 50,000"),
            RecordModel("Expense #2", "18 Dec, 2024", "Myntra", "2000"),
            )

        val adapter = ListAdapter(listMembers)
        //val recycler = requireView().requireViewById<RecyclerView>(R.id.recycler_member)
        dashboardBinding.recyclerMember.layoutManager = LinearLayoutManager(mContext)
        dashboardBinding.recyclerMember.adapter = adapter


        dashboardBinding.AddNewbutton.setOnClickListener {
            navigateToExpenseFragment()
        }
    }

    private fun navigateToExpenseFragment() {
        val fragmentExpense = FragmentExpense.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentExpense)
            .addToBackStack(null) // Adds this transaction to the back stack
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentDash()
    }
}