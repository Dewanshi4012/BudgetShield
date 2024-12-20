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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FragmentDash : Fragment() {

    lateinit var listAdapter: ListAdapter
    lateinit var mContext: Context
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    lateinit var dashboardBinding: FragmentDashboardBinding
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
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardBinding = FragmentDashboardBinding.inflate(inflater,container,false)
        return dashboardBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardBinding.recyclerMember.layoutManager = LinearLayoutManager(mContext)

        fetchDataFromFirestore()


        dashboardBinding.AddNewbutton.setOnClickListener {
            navigateToExpenseFragment()
        }
    }

    private fun fetchDataFromFirestore() {
        val userId = auth.currentUser?.uid ?:return
        val recordsRef = db.collection(userId)

        recordsRef.get()
            .addOnSuccessListener { querySnapshot ->
                val records = mutableListOf<RecordModel>()

                for (document: QueryDocumentSnapshot in querySnapshot) {
                    // Extracting data from the document fields
                    val recordType = document.getString("type") ?: ""
                    val dateOfRecord = document.getString("date") ?: ""
                    val description = document.getString("description") ?: ""
                    val amountPrice = document.getLong("amount") ?: ""
                    // Add the RecordModel to the list
                    records.add(RecordModel(recordType, dateOfRecord, description, amountPrice.toString()))
                }
                // Update RecyclerView with fetched records
                updateRecyclerView(records)
            }
            .addOnFailureListener { exception ->
                println("Error fetching documents: $exception")
            }
    }
    private fun updateRecyclerView(records: List<RecordModel>) {
        if (::listAdapter.isInitialized) {
            listAdapter.updateRecords(records)
        } else {
            listAdapter = ListAdapter(records) { record ->
                // This is the item click callback. You can now pass the selected record to FragmentDesExpense.
                navigateToExpenseFragment(record)
            }
            dashboardBinding.recyclerMember.adapter = listAdapter
        }
    }
    private fun navigateToExpenseFragment(record: RecordModel){
        val fragmentExpense = FragmentDesExpense.newInstance(record)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentExpense)
            .addToBackStack(null) // Adds this transaction to the back stack
            .commit()
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